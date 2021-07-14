package com.benz.movie.info.api.dao;

import com.benz.movie.info.api.entity.RemainderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RemainderDao extends JpaRepository<RemainderItem,Long> {

   /* @Query(value = "select * from REMAINDER_LIST where EMAIL=:email and SERIES_ID=:seriesId",nativeQuery = true)
    Optional<RemainderItem> findRemainderItem(@Param("email") String email, @Param("seriesId") String seriesId);*/

    @Query(value = "from RemainderItem where user.email=:email and tvSeries.seriesId=:seriesId")
    Optional<RemainderItem> findRemainderItem(@Param("email") String email, @Param("seriesId") String seriesId);

    @Query(value = "from RemainderItem where user.email=:email")
    Optional<List<RemainderItem>> getRemainderItemsByUser(String email);
}
