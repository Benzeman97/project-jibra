package com.benz.movie.info.api.model;

import lombok.*;

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
}
