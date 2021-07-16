package com.benz.movie.info.api.dao;

import com.benz.movie.info.api.entity.TvSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TvSeriesDao extends JpaRepository<TvSeries,String> {

    @Query(value = "from TvSeries t where t.seriesName like %:name%")
    Optional<List<TvSeries>> findTvSeriesBySearch(@Param("name") String name);
}
