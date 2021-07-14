package com.benz.movie.info.api.service.impl;

import com.benz.movie.info.api.dao.MovieInfoDao;
import com.benz.movie.info.api.dao.MovieRateDao;
import com.benz.movie.info.api.entity.Movie;
import com.benz.movie.info.api.entity.MovieRate;
import com.benz.movie.info.api.entity.Schedule;
import com.benz.movie.info.api.dto.request.Rating;
import com.benz.movie.info.api.service.MovieUserService;
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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@DisplayName("MovieUserServiceImplTest")
public class MovieUserServiceImplTest {

    @Autowired
    private MovieUserService movieUserService;

    @MockBean
    private MovieRateDao movieRateDao;

    @MockBean
    private MovieInfoDao movieInfoDao;


    @Test
    @DisplayName("RatingBasedOnUserTest")
    public void ratingBasedOnUserTest() {

        String movieName = "Harry Potter 1";
        int rate = 6;

        Movie expectedMovie = getMovieInfo_1();

        MovieRate expectedMovieRate = getMovieRate();

        Mockito.when(movieInfoDao.findMovieByMovieName(expectedMovie.getMovieName())).thenReturn(expectedMovie);

        Mockito.when(movieRateDao.findMovieRateByMovieName(expectedMovieRate.getMovieName())).thenReturn(expectedMovieRate);

        Mockito.when(movieRateDao.save(expectedMovieRate)).thenReturn(expectedMovieRate);

        Rating rating = movieUserService.ratingBasedOnUser(movieName, rate);

        Assertions.assertEquals(expectedMovieRate.getRate(), rating.getRate(), String.format("expected rate should be %.1f but actual was %.1f", expectedMovieRate.getRate(), rating.getRate()));

    }

    private MovieRate getMovieRate() {
        MovieRate movieRate = new MovieRate();
        movieRate.setMovieName("Harry Potter 1");
        movieRate.setRate(6.6);
        movieRate.setRatedUser(50);
        movieRate.setTotalRate(325);
        movieRate.setMovie(getMovieInfo_1());
        return movieRate;
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
