package com.benz.movie.info.api.entity;

import com.benz.movie.info.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "MOVIE_RATE", schema = Schema.MOVIEDB, uniqueConstraints = {
        @UniqueConstraint(name = "movieName", columnNames = "MOVIE_NAME")
})
@Getter
@Setter
public class MovieRate {

    @Id
    @SequenceGenerator(name = "RATED_ID_GEN", sequenceName = Schema.MOVIEDB + ".RATED_ID_SEQ", initialValue = 501, allocationSize = 1)
    @GeneratedValue(generator = "RATED_ID_GEN", strategy = GenerationType.SEQUENCE)
    @Column(name = "RATE_ID")
    private int rateId;
    @Column(name = "MOVIE_NAME", nullable = false)
    private String movieName;
    @Column(name = "RATE", nullable = false)
    private double rate;
    @Column(name = "RATED_USER", nullable = false)
    private long ratedUser;
    @Column(name = "TOTAL_RATE", nullable = false)
    private long totalRate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Movie.class)
    @JoinColumn(name = "MOVIE_ID", referencedColumnName = "MOVIE_ID")
    private Movie movie;
}
