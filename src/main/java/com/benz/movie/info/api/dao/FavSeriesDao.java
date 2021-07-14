package com.benz.movie.info.api.dao;

import com.benz.movie.info.api.entity.FavTvSeries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavSeriesDao extends JpaRepository<FavTvSeries,Long> {

//    @Query(value = "select * from FAVORITE_TVSERIES_LIST where EMAIL=:email and SERIES_ID=:seriesId",nativeQuery = true)
//    Optional<FavTvSeries> findFavTvSeries(@Param("email") String email, @Param("seriesId") String seriesId);

    @Query(value = "from FavTvSeries where user.email=:email and tvSeries.seriesId=:seriesId")
    Optional<FavTvSeries> findFavTvSeries(@Param("email") String email, @Param("seriesId") String seriesId);

    @Query("from FavTvSeries where user.email=:email")
    Optional<List<FavTvSeries>> findFavTvSeriesByUser(@Param("email") String email);
}
