package com.benz.movie.info.api.entity;

import com.benz.movie.info.api.db.Schema;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "VOYAGE_USER",schema = Schema.USERDB)
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Column(nullable = false,length = 120)
    private String fname;
    @Column(nullable = false,length = 120)
    private String lname;
    @Id
    @Column(nullable = false,length = 120)
    private String email;
    @JsonIgnore
    @Column(nullable = false,length = 500)
    private String password;
    private LocalDate dob;
    private String country;

}
