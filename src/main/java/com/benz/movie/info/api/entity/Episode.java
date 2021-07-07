package com.benz.movie.info.api.entity;

import com.benz.movie.info.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "EPISODE",schema = Schema.MOVIEDB)
@Getter
@Setter
public class Episode {

    @Id
    @Column(name = "EPISODE_ID")
    private String episodeId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DURATION")
    private String duration;

}
