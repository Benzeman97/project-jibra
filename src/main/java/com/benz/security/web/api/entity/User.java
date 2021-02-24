package com.benz.security.web.api.entity;

import com.benz.security.web.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "USER90",schema = Schema.TESTDB,uniqueConstraints = {
        @UniqueConstraint(name = "userName",columnNames = "USER_NAME"),
        @UniqueConstraint(name = "email",columnNames = "EMAIL")
})
@Getter
@Setter
public class User {

    @Id
    @SequenceGenerator(name = "USER_ID_GEN",sequenceName = Schema.TESTDB+".USER_ID_SEQ",initialValue = 1003,allocationSize = 1)
    @GeneratedValue(generator = "USER_ID_GEN",strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private int userId;
    @Column(name = "USER_NAME",nullable = false)
    private String userName;
    @Column(name = "PASSWORD",nullable = false)
    private String password;
    @Column(name = "EMAIL",nullable = false)
    private String email;
    @Column(name = "ACTIVE",nullable = false)
    private String active;
    @Column(name = "ACC_NON_EXPIRED")
    private String accNonExpired;
    @Column(name = "CREDENTIAL_NON_EXPIRED")
    private String credentialNonExpired;
    @Column(name = "ACC_NON_LOCKED")
    private String accNonLocked;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")},
    inverseJoinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")})
    private Set<Role> roles;
}
