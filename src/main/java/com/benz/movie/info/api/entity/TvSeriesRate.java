package com.benz.movie.info.api.entity;

import com.benz.movie.info.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TV_SERIES_RATE", schema = Schema.MOVIEDB)
@Getter
@Setter
public class TvSeriesRate {

    @Id
    @SequenceGenerator(name = "SERIES_RATED_ID_GEN",sequenceName = Schema.MOVIEDB+".SERIES_RATED_ID_SEQ",initialValue = 573,allocationSize = 1)
    @GeneratedValue(generator = "SERIES_RATED_ID_GEN",strategy = GenerationType.SEQUENCE)
    @Column(name = "RATE_ID")
    private int rateId;
    @Column(name = "SERIES_NAME")
    private String seriesName;
    @Column(name = "RATE")
    private double rate;
    @Column(name = "RATED_USER")
    private long ratedUser;
    @Column(name = "TOTAL_RATE")
    private double totalRate;

   /* @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,targetEntity = TvSeries.class)
    @JoinColumn(name = "SERIES_ID",referencedColumnName = "SERIES_ID")
    private TvSeries tvSeries;*/

}
