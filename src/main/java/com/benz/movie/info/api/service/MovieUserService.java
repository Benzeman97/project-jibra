package com.benz.movie.info.api.service;

import com.benz.movie.info.api.model.Rating;

public interface MovieUserService {

    Rating ratingBasedOnUser(String movieName, int rate);
}
