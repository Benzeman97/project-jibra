package com.benz.movie.info.api.service.impl;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@ExtendWith({SpringExtension.class})
public class MovieInfoServiceImplTest {

   //final private static Logger LOGGER = LogManager.getLogger(MovieInfoServiceImpl.class);

    @Autowired
    private MovieInfoService movieInfoService;

    @Test
    @DisplayName("SaveMovieInfoTest")
    public void saveMovieInfoTest()
    {
           Movie expectedMovie = getMovieInfo();

        Mockito.when(movieInfoService.getMovieInfo(expectedMovie.getMovieId())).thenReturn(expectedMovie);

          Movie actualMovie = movieInfoService.getMovieInfo(expectedMovie.getMovieId());

        Assertions.assertEquals(expectedMovie,actualMovie,"movie info should be returned with given movie id");

    }

    private Movie getMovieInfo()
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
          movie.setDuration("2.25h");
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
        schedule_1.setScheduleId('K03081');
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        schedule_1.setScreeningTime(ZonedDateTime.parse("08-03-2003 10:15:00 Europe/Paris",dateTimeFormatter));

        Schedule schedule_2=new Schedule();
        schedule_1.setScheduleId('K03081');
        schedule_1.setScreeningTime(ZonedDateTime.parse("08-03-2003 04:15:00 Europe/Paris",dateTimeFormatter));

        Set<Schedule> schedules=new HashSet<>();
        schedules.add(schedule_1);
        schedules.add(schedule_2);

        return schedules;
    }
}
