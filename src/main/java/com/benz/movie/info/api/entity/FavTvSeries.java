package com.benz.movie.info.api.entity;


import com.benz.movie.info.api.db.Schema;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "FAVORITE_TVSERIES_LIST", schema = Schema.MOVIEDB)
@Getter
@Setter
@ToString
public class FavTvSeries {

    @Id
    @SequenceGenerator(name = "FAV_ID_GEN",sequenceName = Schema.MOVIEDB+".FAV_ID_SEQ",initialValue = 8056,allocationSize = 1)
    @GeneratedValue(generator = "FAV_ID_GEN",strategy = GenerationType.SEQUENCE)
    @Column(name = "FAV_SERIES_ID")
    private long favSeriesId;

    @ManyToOne(targetEntity = User.class,cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name = "EMAIL",referencedColumnName = "EMAIL")
    private User user;

    @ManyToOne(targetEntity = TvSeries.class,cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name = "SERIES_ID",referencedColumnName = "SERIES_ID")
    private TvSeries tvSeries;

}
