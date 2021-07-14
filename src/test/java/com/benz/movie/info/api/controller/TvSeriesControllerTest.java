package com.benz.movie.info.api.controller;

import com.benz.movie.info.api.entity.*;
import com.benz.movie.info.api.dto.response.TvSeriesInfo;
import com.benz.movie.info.api.security.AuthEntryPoint;
import com.benz.movie.info.api.service.TvSeriesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TvSeriesController.class)
@ExtendWith({SpringExtension.class})
@DisplayName("TvSeriesControllerTest")
public class TvSeriesControllerTest {

    @MockBean
    private TvSeriesService tvSeriesService;

    @MockBean
    private AuthEntryPoint authEntryPoint;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("getTvSeriesTest")
    public void getTvSeriesTest() throws Exception{

        Mockito.when(tvSeriesService.getTvSeries()).thenReturn(Arrays.asList(tvSeries_1()));

        MvcResult result = mockMvc.perform(get("/api/tv-series"))
                .andExpect(status().isOk())
                .andReturn();

        int expectedResponse = HttpStatus.OK.value();
        int actualResponse = result.getResponse().getStatus();

        Assertions.assertEquals(expectedResponse,actualResponse,()->"expected "+expectedResponse+" but actual was "+actualResponse);
    }

    @Test
    @DisplayName("findTvSeriesRateTest")
    public void findTvSeriesRateTest() throws Exception{

        String name="vikings";

        Mockito.when(tvSeriesService.findTvSeriesRate(Mockito.any(String.class))).thenReturn(tvSeriesRate_1());

       MvcResult result = mockMvc.perform(get("/api/tv-series/rate").param("name",name))
                .andExpect(status().isOk()).andReturn();

       int expectedResponse = HttpStatus.OK.value();
       int actualResponse = result.getResponse().getStatus();

       Assertions.assertEquals(expectedResponse,actualResponse,()->"expected "+expectedResponse+" but actual was "+actualResponse);
    }

    @Test
    @DisplayName("getTvSeriesInfo")
    public void getTvSeriesInfoTest() throws Exception{

        Mockito.when(tvSeriesService.getTvSeriesInfo()).thenReturn(Arrays.asList(tvSeriesInfo_1()));

       MvcResult result = mockMvc.perform(get("/api/tv-series/info"))
                .andExpect(status().isOk()).andReturn();

        int expectedResponse = HttpStatus.OK.value();
        int actualResponse = result.getResponse().getStatus();

        Assertions.assertEquals(expectedResponse,actualResponse,()->"expected "+expectedResponse+" but actual was "+actualResponse);

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
