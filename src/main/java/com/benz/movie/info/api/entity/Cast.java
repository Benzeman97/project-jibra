package com.benz.movie.info.api.entity;

import com.benz.movie.info.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CAST",schema = Schema.MOVIEDB)
@Getter
@Setter
public class Cast {

    @Id
    @Column(name = "CAST_ID",nullable = false)
    private String castId;
    @Column(name = "NAME",nullable = false)
    private String name;
    @Column(name = "IMG_URL")
    private String imgUrl;

}
