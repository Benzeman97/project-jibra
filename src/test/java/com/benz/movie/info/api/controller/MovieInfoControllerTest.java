package com.benz.movie.info.api.controller;

import com.benz.movie.info.api.entity.Movie;
import com.benz.movie.info.api.entity.Schedule;
import com.benz.movie.info.api.security.AuthEntryPoint;
import com.benz.movie.info.api.service.MovieInfoService;
import com.benz.movie.info.api.service.MovieUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebMvcTest
@ExtendWith({SpringExtension.class})
@DisplayName("MovieInfoControllerTest")
public class MovieInfoControllerTest {

    @MockBean
    private MovieInfoService movieInfoService;

    @MockBean
    private MovieUserService movieUserService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthEntryPoint authEntryPoint;

    @Test
    @DisplayName("findMovieInfoTest")
    public void findMovieInfoTest() throws Exception {

        Mockito.when(movieInfoService.findMovieInfo(Mockito.any(String.class))).thenReturn(getMovieInfo_1());

        MvcResult result = mockMvc.perform(get("/movie/{movieId}", getMovieInfo_1().getMovieId()).accept(new String[]{MediaType.APPLICATION_JSON_VALUE})
                .header("Authorization", "bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsibW92aWUtaW5mby1zZXJ2aWNlIiwicGF5bWVudC1zZXJ2aWNlIiwidXNlci1pbmZvLXNlcnZpY2UiLCJ3ZWItcG9ydGFsIl0sInVzZXJfbmFtZSI6Im5hZmF6YmVuemVtYUBnbWFpbC5jb20iLCJzY29wZSI6WyJjcmVhdGUiLCJyZWFkIiwidXBkYXRlIiwiZGVsZXRlIl0sImV4cCI6MTYxOTU1MTUxNCwiYXV0aG9yaXRpZXMiOlsiQ0FOX0NSRUFURSIsIlJPTEVfVVNFUiIsIkNBTl9ERUxFVEUiLCJST0xFX0FETUlOIiwiUk9MRV9NT0RFUkFUT1IiLCJDQU5fUkVBRCIsIkNBTl9VUERBVEUiXSwianRpIjoiMmU2YWEzZGMtOWZiNy00ZTM5LTg2NDUtYTQwNDFhZTBiY2MzIiwiY2xpZW50X2lkIjoid2ViIn0.lLGt_EfggGPS6c-VQ52-UIGhrueVcfn_hpiH9c_6TDUujvf-58PFCxx_eqsLB6ke23UNfqwmMPmHYf3Gytk2n8BlbOVxXVdRv-yAvl2FvS6zhgK4ljXhBhgD7fBZhw2jHh3INR2EfzgunLtlU-76m5ivncN54cYiTl8dXveExOkhI5W-9lsriXROlcrdG8HWAS5XDU7aCdRVv9igc7ijdhBUtUOqik4sQjHsnGOQWafV-iXx-5x_Bk62rVhjWxdn8OLVZaWiK5S9KObb7X3U0UKvdH9gWPiWNBVIawDy16e7hDTJT0DplXqNr-yjKt1trTNBipDPy7nT6CvyyK721A"))
                .andExpect(status().isOk()).andReturn();

        int actualStatus = result.getResponse().getStatus();

        Assertions.assertEquals(actualStatus, HttpStatus.OK.value(), () -> "expected " + HttpStatus.OK.value() + "but response was " + actualStatus);
    }

    @Test
    @DisplayName("getMovieInfoTest")
    public void getMovieInfoTest() throws Exception {
        Mockito.when(movieInfoService.getMovieInfo()).thenReturn(getMovieInfoList());

        MvcResult result = mockMvc.perform(get("/movie").accept(new String[]{MediaType.APPLICATION_JSON_VALUE}))
                .andExpect(status().isOk()).andReturn();

        int actualResponse = result.getResponse().getStatus();

        Assertions.assertEquals(actualResponse, HttpStatus.OK.value(), () -> String.format("expected %d but response was %d", HttpStatus.OK.value(), actualResponse));
    }

    @Test
    @DisplayName("saveMovieInfoTest")
    public void saveMovieInfoTest() throws Exception {
        String movie = new ObjectMapper().writeValueAsString(getMovieInfo_1());

        Mockito.when(movieInfoService.saveMovieInfo(Mockito.any(Movie.class))).thenReturn(getMovieInfo_1());

        MvcResult result = mockMvc.perform(post("/movie/save").contentType(MediaType.APPLICATION_JSON_VALUE).accept(new String[]{MediaType.APPLICATION_JSON_VALUE})
                .content(movie)).andExpect(status().isCreated()).andReturn();

        int actualResponse = result.getResponse().getStatus();

        Assertions.assertEquals(actualResponse, HttpStatus.CREATED.value(), () -> String.format("expected %d but response was %d", HttpStatus.CREATED.value(), actualResponse));
    }

    @Test
    @DisplayName("updateMovieInfoTest")
    public void updateMovieInfoTest() throws Exception {
        Movie movie = getMovieInfo_2();
        movie.setDuration("2.20H");
        movie.setPrice(14.0);
        String updatedMovie = new ObjectMapper().writeValueAsString(movie);

        Mockito.when(movieInfoService.updateMovieInfo(Mockito.any(String.class), Mockito.any(Movie.class))).thenReturn(movie);

        MvcResult result = mockMvc.perform(put("/movie/{movieId}/update", movie.getMovieId()).contentType(MediaType.APPLICATION_JSON_VALUE).accept(new String[]{MediaType.APPLICATION_JSON_VALUE})
                .content(updatedMovie)).andExpect(status().isOk()).andReturn();

        int actualResponse = result.getResponse().getStatus();

        Assertions.assertEquals(actualResponse, HttpStatus.OK.value(), () -> String.format("expected %d but response was %d", HttpStatus.OK.value(), actualResponse));
    }

    @Test
    @DisplayName("deleteMovieInfoTest")
    public void deleteMovieInfoTest() throws Exception {

        MvcResult result = mockMvc.perform(delete("/movie/{movieId}", getMovieInfo_1().getMovieId()))
                .andExpect(status().isOk()).andReturn();

        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus(), () -> String.format("expected %d but response was %d", HttpStatus.OK.value(), result.getResponse().getStatus()));
    }


    private List<Movie> getMovieInfoList() {
        return Arrays.asList(getMovieInfo_1(), getMovieInfo_2());
    }

    private Movie getMovieInfo_1() {
        Movie movie = new Movie();
        movie.setMovieId("HP001");
        movie.setMovieName("Harry Potter 1");
        movie.setOriginCountry("UK");
        movie.setCategory("Kids Movie");
        movie.setPrice(10);
        movie.setLanguage("English");
        movie.setCast("Emma Watson,Ron Wisley,Harry De Soyza");
        movie.setUrlImage("http://");
        movie.setDuration("2.25h");
        movie.setType("2D");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        movie.setReleasedDate(LocalDate.parse("20-05-2000", dateTimeFormatter));
        movie.setDirector("JK Rowling");
        movie.setSchedule(schedules());
        return movie;

    }

    private Movie getMovieInfo_2() {
        Movie movie = new Movie();
        movie.setMovieId("HP002");
        movie.setMovieName("Harry Potter 2");
        movie.setOriginCountry("UK");
        movie.setCategory("Kids Movie");
        movie.setPrice(12);
        movie.setLanguage("English");
        movie.setCast("Emma Watson,Ron Wisley,Harry De Soyza");
        movie.setUrlImage("http://");
        movie.setDuration("2.40h");
        movie.setType("2D");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        movie.setReleasedDate(LocalDate.parse("08-03-2003", dateTimeFormatter));
        movie.setDirector("JK Rowling");
        movie.setSchedule(schedules());
        return movie;

    }

    private Set<Schedule> schedules() {
        Schedule schedule_1 = new Schedule();
        schedule_1.setScheduleId("K03081");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        schedule_1.setScreeningTime(ZonedDateTime.parse("08-03-2003 10:15:00 Europe/Paris", dateTimeFormatter));

        Schedule schedule_2 = new Schedule();
        schedule_1.setScheduleId("K03081");
        schedule_1.setScreeningTime(ZonedDateTime.parse("08-03-2003 04:15:00 Europe/Paris", dateTimeFormatter));

        Set<Schedule> schedules = new HashSet<>();
        schedules.add(schedule_1);
        schedules.add(schedule_2);

        return schedules;
    }

}
