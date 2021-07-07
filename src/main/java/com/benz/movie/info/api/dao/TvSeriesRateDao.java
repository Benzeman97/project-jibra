package com.benz.movie.info.api.dao;

import com.benz.movie.info.api.entity.TvSeriesRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TvSeriesRateDao extends JpaRepository<TvSeriesRate,Integer> {

    Optional<TvSeriesRate> findTvSeriesRateBySeriesName(String name);
}
