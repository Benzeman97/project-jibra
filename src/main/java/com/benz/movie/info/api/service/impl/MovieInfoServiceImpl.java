package com.benz.movie.info.api.service.impl;

import com.benz.movie.info.api.dao.MovieInfoDao;
import com.benz.movie.info.api.entity.Movie;
import com.benz.movie.info.api.exception.DataNotFoundException;
import com.benz.movie.info.api.service.MovieInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieInfoServiceImpl implements MovieInfoService {

    private MovieInfoDao movieInfoDao;

    public MovieInfoServiceImpl(MovieInfoDao movieInfoDao)
    {
        this.movieInfoDao=movieInfoDao;
    }

    @Override
    public List<Movie> getMovieInfo() {
        return null;
    }

    @Override
    public Movie getMovieInfo(String movieId) {
        return movieInfoDao.findById(movieId)
                .orElseThrow(()->new DataNotFoundException(String.format("Movie info is not found with %s",movieId)));
    }
}
