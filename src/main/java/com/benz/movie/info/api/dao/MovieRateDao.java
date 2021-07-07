package com.benz.movie.info.api.dao;

import com.benz.movie.info.api.entity.MovieRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRateDao extends JpaRepository<MovieRate, Long> {

    MovieRate findMovieRateByMovieName(String name);
}
