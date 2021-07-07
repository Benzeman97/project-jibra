package com.benz.movie.info.api.service.impl;

import com.benz.movie.info.api.dao.TvSeriesDao;
import com.benz.movie.info.api.dao.TvSeriesRateDao;
import com.benz.movie.info.api.entity.*;
import com.benz.movie.info.api.model.TvSeriesInfo;
import com.benz.movie.info.api.service.TvSeriesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@DisplayName("TvSeriesServiceImplTest")
public class TvSeriesServiceImplTest {

    @Autowired
    private TvSeriesService tvSeriesService;

    @MockBean
    private TvSeriesDao tvSeriesDao;

    @MockBean
    private TvSeriesRateDao tvSeriesRateDao;

    @Test
    @DisplayName("getTvSeriesTest")
    public void getTvSeriesTest(){

        List<TvSeries> expectedTvSeriesList
                =new ArrayList<>(Arrays.asList(tvSeries_1()));

        Mockito.when(tvSeriesDao.findAll()).thenReturn(expectedTvSeriesList);

         List<TvSeries> actualTvSeriesList = tvSeriesService.getTvSeries();

        Assertions.assertEquals(expectedTvSeriesList,actualTvSeriesList,"all the tv series are returned from mock db");
    }

    @Test
    @DisplayName("findTvSeriesTest")
    public void findTvSeriesTest(){
         String id="V1056";
         TvSeries expectedTvSeries
                 = tvSeries_1();
         Mockito.when(tvSeriesDao.findById(id))
                 .thenReturn(Optional.of(expectedTvSeries));
         TvSeries actualTvSeries
                 = tvSeriesService.findTvSeries(id);

         Assertions.assertEquals(expectedTvSeries,actualTvSeries,"returned specific tv series by id");
    }

    @Test
    @DisplayName("findTvSeriesRateTest")
    public void findTvSeriesRateTest(){
        String name="Vikings";
        TvSeriesRate expectedTvSeriesRate
                =tvSeriesRate_1();

        Mockito.when(tvSeriesRateDao.findTvSeriesRateBySeriesName(name))
                .thenReturn(Optional.of(expectedTvSeriesRate));

        TvSeriesRate actualTvSeriesRate =
                tvSeriesService.findTvSeriesRate(name);

        Assertions.assertEquals(expectedTvSeriesRate,actualTvSeriesRate,"returned tv series rate by name");
    }

    @Test
    @DisplayName("getTvSeriesInfo")
    public void getTvSeriesInfoTest(){
         List<TvSeries> tvSeriesList = new ArrayList<>(Arrays.asList(tvSeries_1()));

         Mockito.when(tvSeriesDao.findAll())
                 .thenReturn(tvSeriesList);

        List<TvSeriesInfo> expectedTvSeriesInfo = new ArrayList<>(Arrays.asList(tvSeriesInfo_1()));

        List<TvSeriesInfo> actualTvSeriesInfo = tvSeriesService.getTvSeriesInfo();

        Assertions.assertEquals(expectedTvSeriesInfo.size(),actualTvSeriesInfo.size(),"returned sorted tv series info");
    }

    private TvSeries tvSeries_1(){
        TvSeries tvSeries=new TvSeries();
        tvSeries.setSeriesId("V1056");
        tvSeries.setSeriesName("Vikings");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        tvSeries.setReleasedDate(LocalDate.parse("23-12-2012",formatter));
        tvSeries.setCountry("USA");
        tvSeries.setLanguage("English");
        tvSeries.setTypes("crime,adventure,thriller");
        tvSeries.setImgUrl("https://viking.jpg");
        tvSeries.setDescription("simple description here");

        Set<Season> seasons=new HashSet<>();
        seasons.add(season_1());
        tvSeries.setSeasons(seasons);

        Set<TvSeriesRate> tvSeriesRateSet = new HashSet<>();
        tvSeriesRateSet.add(tvSeriesRate_1());
        tvSeries.setTvSeriesRate(tvSeriesRateSet);

        Set<Cast> casts=new HashSet<>();
        casts.add(cast_1());
        casts.add(cast_2());
        tvSeries.setCasts(casts);

        return tvSeries;

    }

    private Season season_1(){
        Season season=new Season();
        season.setSeasonId("viki_01");
        season.setSeasonName("vikings_01");
        season.setImgUrl("https://viki-se-01.jpg");
        Set<Episode> episodes=new HashSet<>();
        episodes.add(episode_1());
        season.setEpisodes(episodes);
        return season;
    }
    private Episode episode_1(){
        Episode episode=new Episode();
        episode.setEpisodeId("viki_s01_e01");
        episode.setName("trival");
        episode.setDuration("43 min");
        return episode;
    }

    private TvSeriesRate tvSeriesRate_1(){
        TvSeriesRate tvSeriesRate=new TvSeriesRate();
        tvSeriesRate.setRateId(556);
        tvSeriesRate.setSeriesName("Vikings");
        tvSeriesRate.setRate(8.9);
        tvSeriesRate.setRatedUser(789);
        tvSeriesRate.setTotalRate(2800.0);
        return tvSeriesRate;

    }

    private Cast cast_1(){
        Cast cast=new Cast();
        cast.setCastId("K1045");
        cast.setName("Kelly Brook");
        cast.setImgUrl("https://kelly.jpg");
        return cast;
    }

    private Cast cast_2(){
        Cast cast=new Cast();
        cast.setCastId("N1078");
        cast.setName("Nafaz Benzema");
        cast.setImgUrl("https://benz.jpg");
        return cast;
    }

    private TvSeriesInfo tvSeriesInfo_1(){
        return new TvSeriesInfo("V1056","Vikings","crime,thriller,horror","USA",8.9,"https://vikings.jpa");
    }

}
