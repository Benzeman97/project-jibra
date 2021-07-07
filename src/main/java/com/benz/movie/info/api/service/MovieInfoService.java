package com.benz.movie.info.api.service;

import com.benz.movie.info.api.entity.Movie;

import java.util.List;

public interface MovieInfoService {

    List<Movie> getMovieInfo();

    Movie findMovieInfo(String movieId);

    Movie saveMovieInfo(Movie movie);

    Movie updateMovieInfo(String movieId, Movie movie);

    void deleteMovieInfo(String movieId);
}
