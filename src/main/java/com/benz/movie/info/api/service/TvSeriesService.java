package com.benz.movie.info.api.service;

import com.benz.movie.info.api.dto.request.RequestList;
import com.benz.movie.info.api.entity.TvSeries;
import com.benz.movie.info.api.entity.TvSeriesRate;
import com.benz.movie.info.api.entity.User;
import com.benz.movie.info.api.dto.request.Item;
import com.benz.movie.info.api.dto.request.Rate;
import com.benz.movie.info.api.dto.response.TvSeriesInfo;
import com.benz.movie.info.api.dto.response.UserList;

import java.util.List;

public interface TvSeriesService {

    List<TvSeries> getTvSeries();
    TvSeries findTvSeries(String id);
    TvSeriesRate findTvSeriesRate(String name);
    List<TvSeriesInfo> getTvSeriesInfo();
    TvSeriesInfo findTvSeriesInfo(String id);
    TvSeriesInfo addRating(Rate rate);
    TvSeriesInfo addToFavoriteList(Item item);
    TvSeriesInfo addToRemainderList(Item item);
    User findUser(String email);
    UserList gatFavoriteTvSeriesByUser(RequestList list);
    UserList getRemainderListByUser(RequestList list);
    List<TvSeriesInfo> findTvSeriesInfoBySearch(String name);

}
