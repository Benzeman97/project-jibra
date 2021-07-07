package com.benz.movie.info.api.service.impl;

import com.benz.movie.info.api.dao.TvSeriesDao;
import com.benz.movie.info.api.dao.TvSeriesRateDao;
import com.benz.movie.info.api.entity.TvSeries;
import com.benz.movie.info.api.entity.TvSeriesRate;
import com.benz.movie.info.api.exception.DataNotFoundException;
import com.benz.movie.info.api.model.TvSeriesInfo;
import com.benz.movie.info.api.service.TvSeriesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Service
public class TvSeriesServiceImpl implements TvSeriesService {

    private static final Logger LOGGER= LogManager.getLogger(TvSeriesServiceImpl.class);

    private TvSeriesDao tvSeriesDao;
    private TvSeriesRateDao tvSeriesRateDao;

    public TvSeriesServiceImpl(TvSeriesDao tvSeriesDao, TvSeriesRateDao tvSeriesRateDao) {
        this.tvSeriesDao = tvSeriesDao;
        this.tvSeriesRateDao = tvSeriesRateDao;
    }

    @Override
    public List<TvSeries> getTvSeries() {

        List<TvSeries> tvSeries = tvSeriesDao.findAll();
        LOGGER.info("all the tv series are retrieved from DB");
        return tvSeries;

    }

    @Override
    public TvSeries findTvSeries(String id) {
        TvSeries tvSeries= tvSeriesDao.findById(id)
                .orElseThrow(()->new DataNotFoundException(String.format("tv series is not found with %s",id)));
        LOGGER.info(String.format("tv series is found with %s",id));
        return tvSeries;
    }

    @Override
    public TvSeriesRate findTvSeriesRate(String name) {
        TvSeriesRate tvSeriesRate= tvSeriesRateDao.findTvSeriesRateBySeriesName(name)
                .orElseThrow(() -> new DataNotFoundException(String.format("tv series rate is not found with %s", name)));
        LOGGER.info(String.format("tv series rate is found with %s",name));
        return tvSeriesRate;
    }

    @Override
    public List<TvSeriesInfo> getTvSeriesInfo() {
        List<TvSeries> tvSeriesList = getTvSeries();

        List<TvSeriesInfo> tvSeriesInfoByRate = sortTvSeriesInfoByRate(tvSeriesList);

        List<TvSeriesInfo> tvSeriesInfoByDate = sortTvSeriesInfoByDate(tvSeriesList);

        LOGGER.info("sorted tv series info(s) are returned");
        return filterTvServiceInfo(tvSeriesInfoByDate,tvSeriesInfoByDate,tvSeriesList);

    }

    private List<TvSeriesInfo> sortTvSeriesInfoByRate(List<TvSeries> tvSeriesList){

        List<TvSeriesInfo> tvSeriesInfoByRate =
                tvSeriesList.stream().flatMap(s -> s.getTvSeriesRate().stream()
                        .map(r -> new TvSeriesInfo(s.getSeriesId(), s.getSeriesName(), s.getTypes(), s.getCountry(), r.getRate(), s.getImgUrl())))
                        .collect(Collectors.toList());

        Collections.sort(tvSeriesInfoByRate, Comparator.comparingDouble(TvSeriesInfo::getRate).reversed());

        LOGGER.info("tv series info is sorted by rate");

        return tvSeriesInfoByRate;

    }

    private List<TvSeriesInfo> sortTvSeriesInfoByDate(List<TvSeries> tvSeriesList){

        List<TvSeriesInfo> tvSeriesInfoByDate =
                tvSeriesList.stream().sorted(Comparator.comparing(TvSeries::getReleasedDate).reversed())
                        .flatMap(s -> s.getTvSeriesRate().stream().map(r -> new TvSeriesInfo(s.getSeriesId(), s.getSeriesName(),
                                s.getTypes(), s.getCountry(), r.getRate(), s.getImgUrl()))).collect(Collectors.toList());

        LOGGER.info("tv series info is sorted by date");

        return tvSeriesInfoByDate;
    }

    private List<TvSeriesInfo> filterTvServiceInfo(List<TvSeriesInfo> tvSeriesInfoByDate,
                                                   List<TvSeriesInfo> tvSeriesInfoByRate,List<TvSeries> tvSeriesList)
    {

        int sortPerItem =8;
        int traverse = tvSeriesList.size() / sortPerItem;


        if (tvSeriesList.size() % sortPerItem != 0)
            traverse++;

        List<TvSeriesInfo> tvSeriesInfos = new ArrayList<>();
        int start= 0;
        int end= 4;
        int increment = 4;
        int c=1;

        while (c < traverse) {

            int count=0;

            for(int i=start;i<end;i++)
                if (!filter(tvSeriesInfos,tvSeriesInfoByDate.get(i),(series,tvSeries)->series.getId().equalsIgnoreCase(tvSeries.getId()))) {
                    count++;
                    ++end;
                } else
                    tvSeriesInfos.add(tvSeriesInfoByDate.get(i));

            end=end-count;
            count=0;

            for(int i=start;i<end;i++)
                if (!filter(tvSeriesInfos,tvSeriesInfoByRate.get(i),(series,tvSeries)->series.getId().equalsIgnoreCase(tvSeries.getId()))) {
                    count++;
                    ++end;
                }
                else
                    tvSeriesInfos.add(tvSeriesInfoByRate.get(i));

            start=(end-count);
            end= (start+increment);
            c++;
        }

        tvSeriesInfoByDate.stream()
                .filter(s->filter(tvSeriesInfos,s,(series,tvSeries)->series.getId().equalsIgnoreCase(tvSeries.getId())))
                .forEach(info->tvSeriesInfos.add(info));


        tvSeriesInfoByRate.stream()
                .filter(s->filter(tvSeriesInfos,s,(series,tvSeries)->series.getId().equalsIgnoreCase(tvSeries.getId())))
                .forEach(info->tvSeriesInfos.add(info));

        return tvSeriesInfos;
    }


    private boolean filter(List<TvSeriesInfo> tvSeriesInfoList,TvSeriesInfo tvSeries,
                           BiPredicate<TvSeriesInfo,TvSeriesInfo> biPredicate) {

        for (TvSeriesInfo series : tvSeriesInfoList)
            if (biPredicate.test(series,tvSeries))
                return false;
        return true;

    }

}
