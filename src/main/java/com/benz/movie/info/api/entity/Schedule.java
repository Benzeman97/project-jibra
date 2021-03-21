package com.benz.movie.info.api.entity;

import com.benz.movie.info.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "SCHEDULE",schema = Schema.MOVIEDB)
@Getter
@Setter
public class Schedule {
    @Id
    @Column(name = "SCHEDULE_ID")
    private String scheduleId;
    @Column(name = "SCREENING_TIME")
    private ZonedDateTime screeningTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movieId",referencedColumnName = "MOVIE_ID")
    private Movie movie;
}
