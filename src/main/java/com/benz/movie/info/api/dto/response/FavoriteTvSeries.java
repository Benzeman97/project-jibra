package com.benz.movie.info.api.dto.response;

import com.benz.movie.info.api.entity.TvSeries;
import com.benz.movie.info.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavoriteTvSeries {

    private long id;
    private User user;
    private TvSeries tvSeries;
}
