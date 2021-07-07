package com.benz.movie.info.api.entity;

import com.benz.movie.info.api.db.Schema;
import com.benz.movie.info.api.utility.ZonedDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "SCHEDULE", schema = Schema.MOVIEDB)
@Getter
@Setter
public class Schedule {

    @Id
    @Column(name = "SCHEDULE_ID")
    private String scheduleId;
    @Column(name = "SCREENING_TIME")
    @JsonDeserialize(using = ZonedDateTimeDeserializer.class)
    private ZonedDateTime screeningTime;

   /* @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "MOVIE_ID",referencedColumnName = "MOVIE_ID")
    private Movie movie;*/
}
