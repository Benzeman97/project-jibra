package com.benz.movie.info.api.entity;

import com.benz.movie.info.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "MOVIE", schema = Schema.MOVIEDB, uniqueConstraints = {
        @UniqueConstraint(name = "movieName", columnNames = "MOVIE_NAME")
})
@Getter
@Setter
public class Movie {

    @Id
    @Column(name = "MOVIE_ID")
    private String movieId;
    @Column(name = "MOVIE_NAME", nullable = false)
    private String movieName;
    @Column(name = "ORIGIN_COUNTRY", nullable = false)
    private String originCountry;
    @Column(name = "CATEGORY", nullable = false)
    private String category;
    @Column(name = "PRICE", nullable = false)
    private double price;
    @Column(name = "LANGUAGE", nullable = false)
    private String language;
    @Column(name = "CAST")
    private String cast;
    @Column(name = "URL_IMAGE", nullable = false)
    private String urlImage;
    @Column(name = "DURATION", nullable = false)
    private String duration;
    @Column(name = "TYPE", nullable = false)
    private String type;
    @Column(name = "RELEASED_DATE", nullable = false)
    private LocalDate releasedDate;
    @Column(name = "DIRECTOR")
    private String director;

    @OneToMany(targetEntity = Schedule.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "MOVIE_ID", referencedColumnName = "MOVIE_ID")
    private Set<Schedule> schedule;

}
