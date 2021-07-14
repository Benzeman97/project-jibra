package com.benz.movie.info.api.service.impl;

import com.benz.movie.info.api.dao.MovieInfoDao;
import com.benz.movie.info.api.dao.MovieRateDao;
import com.benz.movie.info.api.entity.Movie;
import com.benz.movie.info.api.entity.MovieRate;
import com.benz.movie.info.api.exception.DataNotFoundException;
import com.benz.movie.info.api.dto.request.Rating;
import com.benz.movie.info.api.service.MovieUserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class MovieUserServiceImpl implements MovieUserService {

    private MovieRateDao movieRateDao;
    private MovieInfoDao movieInfoDao;

    public MovieUserServiceImpl(MovieRateDao movieRateDao, MovieInfoDao movieInfoDao) {
        this.movieRateDao = movieRateDao;
        this.movieInfoDao = movieInfoDao;
    }

    @Override
    public Rating ratingBasedOnUser(String movieName, int rate) {

        Movie movie = movieInfoDao.findMovieByMovieName(movieName);

        if (Objects.isNull(movie))
            throw new DataNotFoundException(String.format("Movie is not found with %s", movieName));

        MovieRate movieRate = movieRateDao.findMovieRateByMovieName(movieName);

        if (Objects.isNull(movieRate)) {
            movieRate = new MovieRate();
            movieRate.setMovieName(movie.getMovieName());
        }
        long users = movieRate.getRatedUser();
        movieRate.setRatedUser(++users);

        Rating rating = getRating(movieRate, rate);

        movieRate.setRate(rating.getRate());
        movieRate.setTotalRate(rating.getTotalPoints());
        movieRate.setMovie(movie);

        movieRateDao.save(movieRate);

        return rating;
    }

    private Rating getRating(MovieRate movieRate, int rate) {

        long totalPoints = movieRate.getRatedUser() * 10;
        long points = movieRate.getTotalRate() + rate;

        double setStar = (double) (100 * points) / (double) totalPoints;
        int star = 0;

        if (Math.round(setStar) >= 90)
            star = 5;
        else if (Math.round(setStar) >= 75)
            star = 4;
        else if (Math.round(setStar) >= 60)
            star = 3;
        else if (Math.round(setStar) >= 40)
            star = 2;
        else
            star = 1;

        double newRate = (double) (points * 10) / (double) totalPoints;

        newRate = Math.round(newRate * 10) / 10;

        return new Rating(newRate, star, points);
    }
}
