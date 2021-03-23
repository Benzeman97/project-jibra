package com.benz.movie.info.api.service.impl;

import com.benz.movie.info.api.dao.MovieInfoDao;
import com.benz.movie.info.api.entity.Movie;
import com.benz.movie.info.api.exception.DataNotFoundException;
import com.benz.movie.info.api.exception.MovieExistedException;
import com.benz.movie.info.api.service.MovieInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MovieInfoServiceImpl implements MovieInfoService {

    private MovieInfoDao movieInfoDao;

    public MovieInfoServiceImpl(MovieInfoDao movieInfoDao)
    {
        this.movieInfoDao=movieInfoDao;
    }

    @Override
    public List<Movie> getMovieInfo() {
        List<Movie> movies = movieInfoDao.findAll();

        if(movies.size()==0)
             throw new DataNotFoundException("No Movie Info available in the db");
        return movies;
    }

    @Override
    public Movie findMovieInfo(String movieId) {
        return movieInfoDao.findById(movieId)
                .orElseThrow(()->new DataNotFoundException(String.format("Movie info is not found with %s",movieId)));
    }

    @Override
    public Movie saveMovieInfo(Movie movie) {

        Movie mov = movieInfoDao.findById(movie.getMovieId()).orElse(null);

        if(Objects.nonNull(mov))
               throw new MovieExistedException(String.format("Movie is existed with id %s",mov.getMovieId()));
        return movieInfoDao.save(movie);

    }

    @Override
    public Movie updateMovieInfo(String movieId, Movie movie) {
         Movie mov = movieInfoDao.findById(movieId).orElse(null);

         if(Objects.isNull(mov))
             throw new DataNotFoundException(String.format("Movie Info is not found with %s",movieId));
         mov.setMovieName(movie.getMovieName());
         mov.setOriginCountry(movie.getOriginCountry());
         mov.setCategory(movie.getCategory());
         mov.setPrice(movie.getPrice());
         mov.setLanguage(movie.getLanguage());
         mov.setCast(movie.getCast());
         mov.setUrlImage(movie.getUrlImage());
         mov.setDuration(movie.getDuration());
         mov.setType(movie.getType());
         mov.setReleasedDate(movie.getReleasedDate());
         mov.setDirector(movie.getDirector());
         mov.setSchedule(movie.getSchedule());

         return movieInfoDao.save(mov);
    }

    @Override
    public void deleteMovieInfo(String movieId) {
        Movie movie= movieInfoDao.findById(movieId).orElse(null);

        if(Objects.isNull(movie))
            throw new DataNotFoundException(String.format("Movie Info is not found with %s",movieId));
        movieInfoDao.delete(movie);
    }
}
