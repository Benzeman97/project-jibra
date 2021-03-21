package com.benz.movie.info.api.dao;

import com.benz.movie.info.api.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieInfoDao extends JpaRepository<Movie,String> {
}
