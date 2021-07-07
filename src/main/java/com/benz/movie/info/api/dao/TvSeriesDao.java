package com.benz.movie.info.api.dao;

import com.benz.movie.info.api.entity.TvSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TvSeriesDao extends JpaRepository<TvSeries,String> {
}
