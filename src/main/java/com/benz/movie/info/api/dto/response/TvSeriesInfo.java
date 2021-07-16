package com.benz.movie.info.api.dto.response;

import com.benz.movie.info.api.entity.Cast;
import com.benz.movie.info.api.entity.Season;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TvSeriesInfo {

    private String id;
    private String name;
    private String types;
    private String country;
    private double rate;
    private String img;
    private String releasedDate;
    private String language;
    private String desc;
    private long ratedUser;
    private String c_code;
    private List<Cast> casts;
    private List<Season> seasons;
}
