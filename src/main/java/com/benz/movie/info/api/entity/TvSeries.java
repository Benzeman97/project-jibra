package com.benz.movie.info.api.entity;

import com.benz.movie.info.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "TV_SERIES", schema = Schema.MOVIEDB,uniqueConstraints = {
        @UniqueConstraint(name = "seriesId",columnNames = "SERIES_ID")
})
@Getter
@Setter
public class TvSeries {

    @Id
    @Column(name = "SERIES_ID")
    private String seriesId;
    @Column(name = "SERIES_NAME")
    private String seriesName;
    @Column(name = "RELEASED_DATE")
    private LocalDate releasedDate;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "LANGUAGE")
    private String language;
    @Column(name = "TYPES")
    private String types;
    @Column(name = "IMG_URL")
    private String imgUrl;
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(targetEntity = Season.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "SERIES_ID",referencedColumnName = "SERIES_ID")
    private Set<Season> seasons;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,targetEntity = TvSeriesRate.class)
    @JoinColumn(name = "SERIES_ID",referencedColumnName = "SERIES_ID")
    private Set<TvSeriesRate> tvSeriesRate;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "TV_SERIES_CAST",
    joinColumns = {@JoinColumn(name = "SERIES_ID",referencedColumnName = "SERIES_ID")},
    inverseJoinColumns = {@JoinColumn(name = "CAST_ID",referencedColumnName = "CAST_ID")})
    private Set<Cast> casts;

}
