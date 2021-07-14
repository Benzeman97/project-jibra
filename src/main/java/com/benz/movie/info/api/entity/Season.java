package com.benz.movie.info.api.entity;

import com.benz.movie.info.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "SEASON",schema = Schema.MOVIEDB)
@Getter
@Setter
public class Season {

    @Id
    @Column(name = "SEASON_ID",nullable = false)
    private String seasonId;
    @Column(name = "SEASON_NAME",nullable = false)
    private String seasonName;
    @Column(name = "IMG_URL")
    private String imgUrl;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "SEASON_EPISODE",
    joinColumns = {@JoinColumn(name = "SEASON_ID",referencedColumnName = "SEASON_ID")},
    inverseJoinColumns = {@JoinColumn(name = "EPISODE_ID",referencedColumnName = "EPISODE_ID")})
    private Set<Episode> episodes;

}
