package com.benz.movie.info.api.service;

import com.benz.movie.info.api.entity.TvSeries;
import com.benz.movie.info.api.entity.TvSeriesRate;
import com.benz.movie.info.api.model.TvSeriesInfo;

import java.util.List;

public interface TvSeriesService {

    List<TvSeries> getTvSeries();
    TvSeries findTvSeries(String id);
    TvSeriesRate findTvSeriesRate(String name);
    List<TvSeriesInfo> getTvSeriesInfo();

}
