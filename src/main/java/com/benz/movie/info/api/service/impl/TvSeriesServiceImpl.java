package com.benz.movie.info.api.service.impl;

import com.benz.movie.info.api.dao.*;
import com.benz.movie.info.api.dto.request.RequestList;
import com.benz.movie.info.api.entity.*;
import com.benz.movie.info.api.exception.DataNotFoundException;
import com.benz.movie.info.api.dto.request.Item;
import com.benz.movie.info.api.dto.request.Rate;
import com.benz.movie.info.api.dto.response.TvSeriesInfo;
import com.benz.movie.info.api.dto.response.UserList;
import com.benz.movie.info.api.service.TvSeriesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class TvSeriesServiceImpl implements TvSeriesService {

    private static final Logger LOGGER= LogManager.getLogger(TvSeriesServiceImpl.class);

    private TvSeriesDao tvSeriesDao;
    private TvSeriesRateDao tvSeriesRateDao;
    private FavSeriesDao favSeriesDao;
    private RemainderDao remainderDao;
    private UserDao userDao;

    public TvSeriesServiceImpl(TvSeriesDao tvSeriesDao, TvSeriesRateDao tvSeriesRateDao,
                               FavSeriesDao favSeriesDao,RemainderDao remainderDao,UserDao userDao) {
        this.tvSeriesDao = tvSeriesDao;
        this.tvSeriesRateDao = tvSeriesRateDao;
        this.favSeriesDao=favSeriesDao;
        this.remainderDao=remainderDao;
        this.userDao=userDao;
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
        return filterTvServiceInfo(tvSeriesInfoByDate,tvSeriesInfoByRate,tvSeriesList);

    }

    @Override
    public TvSeriesInfo findTvSeriesInfo(String id) {
          TvSeries tvSeries = tvSeriesDao.findById(id)
                  .orElseThrow(()->new DataNotFoundException(String.format("tv series is not found with %s",id)));

        List<TvSeriesRate> tvSeriesRate = tvSeries.getTvSeriesRate().stream().map(rate->rate).collect(Collectors.toList());

        return getTvSeriesInfo(tvSeries,tvSeriesRate.get(0));
    }

    @Override
    public TvSeriesInfo addRating(Rate rate) {


        TvSeries tvSeries=findTvSeries(rate.getType_id());

        TvSeriesRate seriesRate = new ArrayList<>(tvSeries.getTvSeriesRate())
                .get(0);

        List<User> users = seriesRate.getUsers().stream().filter(u->u.getEmail().equals(rate.getEmail()))
                .collect(Collectors.toList());

        if(users.size()>=1)
        {
            User user=users.get(0);
            LOGGER.info(String.format("%s already voted for %s", user.getEmail(),tvSeries.getSeriesName()));
            return getTvSeriesInfo(tvSeries,seriesRate);
        }

        User user = findUser(rate.getEmail());

       seriesRate = calculateRate(seriesRate,rate,user);

       Set<TvSeriesRate> tvSeriesRates=new HashSet<>();
       tvSeriesRates.add(seriesRate);

       tvSeries.setTvSeriesRate(tvSeriesRates);

       tvSeries = tvSeriesDao.save(tvSeries);

       return getTvSeriesInfo(tvSeries,seriesRate);

    }

    @Override
    public TvSeriesInfo addToFavoriteList(Item item) {
        FavTvSeries favTvSeries = favSeriesDao.findFavTvSeries(item.getEmail(),item.getType_id())
                .orElse(null);

        TvSeries tvSeries = findTvSeries(item.getType_id());

        TvSeriesRate tvSeriesRate = new ArrayList<>(tvSeries.getTvSeriesRate()).get(0);

        if(Objects.nonNull(favTvSeries)) {
            LOGGER.info(String.format("%s is available in your favorite list",tvSeries.getSeriesName()));
            return getTvSeriesInfo(tvSeries,tvSeriesRate);
        }

        User user = findUser(item.getEmail());

         FavTvSeries favorite=new FavTvSeries();
         favorite.setUser(user);
         favorite.setTvSeries(tvSeries);

         favSeriesDao.save(favorite);

         return getTvSeriesInfo(tvSeries,tvSeriesRate);
    }

    @Override
    public TvSeriesInfo addToRemainderList(Item item) {

         RemainderItem remainderItem = remainderDao.findRemainderItem(item.getEmail(),item.getType_id())
                 .orElse(null);

         TvSeries tvSeries = findTvSeries(item.getType_id());

        TvSeriesRate tvSeriesRate = new ArrayList<>(tvSeries.getTvSeriesRate()).get(0);

        if(Objects.nonNull(remainderItem)){
             LOGGER.info(String.format("%s is available in your remainder list",tvSeries.getSeriesName()));
             return getTvSeriesInfo(tvSeries,tvSeriesRate);
        }

        User user = findUser(item.getEmail());

        RemainderItem remainder =new RemainderItem();
         remainder.setUser(user);
         remainder.setTvSeries(tvSeries);

         remainderDao.save(remainder);

         return getTvSeriesInfo(tvSeries,tvSeriesRate);
    }

    @Override
    public User findUser(String email) {

        User user =userDao.findById(email)
                .orElseThrow(()->{
                    LOGGER.error(String.format("user is not found with %s",email));
                    return new DataNotFoundException(String.format("user is not found with %s",email));
                });

        LOGGER.info(String.format("user is found with %s",email));
        return user;
    }

    @Override
    public UserList gatFavoriteTvSeriesByUser(RequestList reqList) {

         List<FavTvSeries> favTvSeries = favSeriesDao.findFavTvSeriesByUser(reqList.getEmail()).orElse(new ArrayList<>());


         if(checkUserWithFavoriteList(list->list.size()<=0,favTvSeries))
         {
             LOGGER.warn(String.format("user is not found with %s",reqList.getEmail()));
             throw new DataNotFoundException(String.format("user is not found with %s",reqList.getEmail()));
         }

         List<TvSeriesInfo> tvSeriesInfos = favTvSeries.stream().flatMap(t->t.getTvSeries().getTvSeriesRate().stream()
         .map(r->getTvSeriesInfo(t.getTvSeries(),r))).collect(Collectors.toList());

         User user = favTvSeries.get(0).getUser();

         return new UserList(user,tvSeriesInfos);

    }

    @Override
    public UserList getRemainderListByUser(RequestList reqList) {

          List<RemainderItem> remainderItems = remainderDao.getRemainderItemsByUser(reqList.getEmail()).orElse(new ArrayList<>());

            if(checkUserWithRemainderList(list->list.size()<=0,remainderItems)){
                LOGGER.warn(String.format("user is not found with %s",reqList.getEmail()));
                throw new DataNotFoundException(String.format("user is not found with %s",reqList.getEmail()));
            }

           List<TvSeriesInfo> tvSeriesInfos = remainderItems.stream().flatMap(rem->rem.getTvSeries().getTvSeriesRate()
            .stream().map(rate->getTvSeriesInfo(rem.getTvSeries(),rate))).collect(Collectors.toList());

            User user = remainderItems.get(0).getUser();

            return new UserList(user,tvSeriesInfos);
    }

    private boolean checkUserWithRemainderList(Predicate<List<RemainderItem>> predicate,List<RemainderItem> remainderItems){
         return predicate.test(remainderItems);
    }

    private boolean checkUserWithFavoriteList(Predicate<List<FavTvSeries>> predicate,List<FavTvSeries> favTvSeries){
        return predicate.test(favTvSeries);
    }


    private TvSeriesInfo getTvSeriesInfo(TvSeries tvSeries,TvSeriesRate tvSeriesRate){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        return new TvSeriesInfo(tvSeries.getSeriesId(),tvSeries.getSeriesName(),tvSeries.getTypes(),
                tvSeries.getCountry(),tvSeriesRate.getRate(),tvSeries.getImgUrl(),tvSeries.getReleasedDate().format(formatter),
                tvSeries.getLanguage(),tvSeries.getDescription(),tvSeriesRate.getRatedUser(),tvSeries.getCountryCode(),
                tvSeries.getCasts(),tvSeries.getSeasons().stream().sorted(Comparator.comparing(Season::getSeasonName)).collect(Collectors.toList()));

    }

    private TvSeriesRate calculateRate(TvSeriesRate tvSeriesRate,Rate rate,User user){
        int maximumRate=5;
        double totalRate = tvSeriesRate.getTotalRate();
        long ratedUsers = tvSeriesRate.getRatedUser();
        totalRate += rate.getRate();
        ratedUsers++;

        double sumOfRate = ratedUsers *  maximumRate;

        double newRate = ((totalRate/sumOfRate) * 10);


        DecimalFormat decimalFormat=new DecimalFormat("#.##");

        newRate = (Double.valueOf(decimalFormat.format(newRate))*10);

        newRate= ((double)(int)newRate/10);

        Set<User> users=new HashSet<>();
        users.add(user);

        return new TvSeriesRate(tvSeriesRate.getRateId(),tvSeriesRate.getSeriesName(),
                newRate,ratedUsers,totalRate,users);

    }

    private List<TvSeriesInfo> sortTvSeriesInfoByRate(List<TvSeries> tvSeriesList){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        List<TvSeriesInfo> tvSeriesInfoByRate =
                tvSeriesList.stream().flatMap(s -> s.getTvSeriesRate().stream()
                        .map(r -> new TvSeriesInfo(s.getSeriesId(), s.getSeriesName(), s.getTypes(), s.getCountry(), r.getRate(), s.getImgUrl(),s.getReleasedDate().format(formatter)
                                ,s.getLanguage(),s.getDescription(),r.getRatedUser(),s.getCountryCode(),s.getCasts()
                        ,new ArrayList<Season>(s.getSeasons().stream().sorted(Comparator.comparing(Season::getSeasonName)).collect(Collectors.toList())))))
                        .collect(Collectors.toList());

        Collections.sort(tvSeriesInfoByRate, Comparator.comparingDouble(TvSeriesInfo::getRate).reversed());

        LOGGER.info("tv series info is sorted by rate");

        return tvSeriesInfoByRate;

    }

    private List<TvSeriesInfo> sortTvSeriesInfoByDate(List<TvSeries> tvSeriesList){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");

        List<TvSeriesInfo> tvSeriesInfoByDate =
                tvSeriesList.stream().sorted(Comparator.comparing(TvSeries::getReleasedDate).reversed())
                        .flatMap(s -> s.getTvSeriesRate().stream().map(r -> new TvSeriesInfo(s.getSeriesId(), s.getSeriesName(),
                                s.getTypes(), s.getCountry(), r.getRate(), s.getImgUrl(),s.getReleasedDate().format(formatter),s.getLanguage(),s.getDescription(),r.getRatedUser(),
                                s.getCountryCode(),s.getCasts(),new ArrayList<Season>(s.getSeasons().stream().sorted(Comparator.comparing(Season::getSeasonName)).collect(Collectors.toList())))
                        )).collect(Collectors.toList());

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
