package com.benz.movie.info.api.entity;

import com.benz.movie.info.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "REMAINDER_LIST", schema = Schema.MOVIEDB)
@Getter
@Setter
public class RemainderItem {

    @Id
    @SequenceGenerator(name = "REMAINDER_ID_GEN",sequenceName = Schema.MOVIEDB+".REMAINDER_ID_SEQ",initialValue = 8056,allocationSize = 1)
    @GeneratedValue(generator = "REMAINDER_ID_GEN",strategy = GenerationType.SEQUENCE)
    @Column(name = "REMAINDER_ID")
    private long remainderId;

    @ManyToOne(targetEntity = User.class,cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name = "EMAIL",referencedColumnName = "EMAIL")
    private User user;

    @ManyToOne(targetEntity = TvSeries.class,cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn(name = "SERIES_ID",referencedColumnName = "SERIES_ID")
    private TvSeries tvSeries;
}
