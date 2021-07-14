package com.benz.movie.info.api.dto.response;

import com.benz.movie.info.api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserList {

    private User user;
    private List<TvSeriesInfo> tvSeriesInfo;
}
