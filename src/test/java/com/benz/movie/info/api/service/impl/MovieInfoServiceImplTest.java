package com.benz.movie.info.api.service.impl;


import com.benz.movie.info.api.dao.MovieInfoDao;
import com.benz.movie.info.api.entity.Movie;
import com.benz.movie.info.api.entity.Schedule;
import com.benz.movie.info.api.service.MovieInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.util.*;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@DisplayName("MovieInfoServiceImplTest")
public class MovieInfoServiceImplTest {

   final private static Logger LOGGER = LogManager.getLogger(MovieInfoServiceImpl.class);

    @Autowired
    private MovieInfoService movieInfoService;

    @MockBean
    private MovieInfoDao movieInfoDao;

    @Test
    @DisplayName("findMovieInfoTest")
    public void findMovieInfoTest()
    {
           Movie expectedMovie = getMovieInfo_1();

        Mockito.when(movieInfoDao.findById(expectedMovie.getMovieId())).thenReturn(Optional.of(expectedMovie));

          Movie actualMovie = movieInfoService.findMovieInfo(expectedMovie.getMovieId());

        Assertions.assertEquals(expectedMovie,actualMovie,"movie info should be returned with given movie id");

    }

    @Test
    @DisplayName("getMovieInfoTest")
    public void getMovieInfoTest()
    {
          List<Movie> expectedMovieList= getMovieInfoList();

          Mockito.when(movieInfoDao.findAll()).thenReturn(expectedMovieList);

          List<Movie> actualMovieList= movieInfoService.getMovieInfo();

          Assertions.assertEquals(expectedMovieList,actualMovieList,"all the movies which are available in the list returned");
    }

    @Test
    @DisplayName("saveMovieInfoTest")
    public void saveMovieInfoTest()
    {

        Movie expectedMovie = getMovieInfo_2();

        Mockito.when(movieInfoDao.save(expectedMovie)).thenReturn(expectedMovie);

        Movie actualMovie = movieInfoService.saveMovieInfo(expectedMovie);

        Assertions.assertEquals(expectedMovie,actualMovie,"Movie Info should be saved and returned");
    }

    @Test
    @DisplayName("updateMovieInfoTest")
    public void updateMovieInfoTest()
    {
        Movie expectedMovie=getMovieInfo_1();
        expectedMovie.setMovieName("Harry Potter and the Philosopher's Stone");
        expectedMovie.setCategory("Fantasy/Family");
        expectedMovie.setPrice(15);

        Mockito.when(movieInfoDao.findById(expectedMovie.getMovieId())).thenReturn(Optional.of(expectedMovie));

        Mockito.when(movieInfoDao.save(expectedMovie)).thenReturn(expectedMovie);

        Movie actualMovie = movieInfoService.updateMovieInfo(expectedMovie.getMovieId(),expectedMovie);

        Assertions.assertEquals(expectedMovie,actualMovie,"Movie info should be updated and returned");

    }

    @Test
    @DisplayName("deleteMovieInfoTest")
    public void deleteMovieInfoTest()
    {
         Movie movie = getMovieInfo_1();

        Mockito.when(movieInfoDao.findById(movie.getMovieId())).thenReturn(Optional.of(movie));

        movieInfoService.deleteMovieInfo(movie.getMovieId());

         Mockito.verify(movieInfoDao,Mockito.times(1)).delete(movie);
    }

    private List<Movie> getMovieInfoList()
    {
        return Arrays.asList(getMovieInfo_1(),getMovieInfo_2());
    }

    private Movie getMovieInfo_1()
    {
          Movie movie=new Movie();
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
          DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
          movie.setReleasedDate(LocalDate.parse("20-05-2000",dateTimeFormatter));
          movie.setDirector("JK Rowling");
          movie.setSchedule(schedules());
          return movie;

    }

    private Movie getMovieInfo_2()
    {
        Movie movie=new Movie();
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
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        movie.setReleasedDate(LocalDate.parse("08-03-2003",dateTimeFormatter));
        movie.setDirector("JK Rowling");
        movie.setSchedule(schedules());
        return movie;

    }

    private Set<Schedule> schedules()
    {
        Schedule schedule_1=new Schedule();
        schedule_1.setScheduleId("K03081");
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        schedule_1.setScreeningTime(ZonedDateTime.parse("08-03-2003 10:15:00 Europe/Paris",dateTimeFormatter));

        Schedule schedule_2=new Schedule();
        schedule_1.setScheduleId("K03081");
        schedule_1.setScreeningTime(ZonedDateTime.parse("08-03-2003 04:15:00 Europe/Paris",dateTimeFormatter));

        Set<Schedule> schedules=new HashSet<>();
        schedules.add(schedule_1);
        schedules.add(schedule_2);

        return schedules;
    }
}
