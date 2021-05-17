package com.benz.authorization.server.api.entity;

import com.benz.authorization.server.api.db.Schema;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "USERTABLE",schema = Schema.USERDB,uniqueConstraints = {
      @UniqueConstraint(name = "email",columnNames = "EMAIL")
})
@Getter
@Setter
@IdClass(UserIdentity.class)
public class User {

    @Id
    @SequenceGenerator(name = "USER_ID_GEN", sequenceName = Schema.USERDB + ".USER_ID_SEQ", initialValue = 56001, allocationSize = 1)
    @GeneratedValue(generator = "USER_ID_GEN", strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private long userId;
    @Column(name = "FIRST_NAME",nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL",nullable = false)
    private String email;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "DOB")
    private Date dob;
    @Column(name = "TELE_NO")
    private String teleNo;
    @Column(name = "PASSWORD",nullable = false)
    private String password;
    @Id
    @Column(name = "NIC_OR_PASSPORT")
    private String nicOrPassport;
    @Column(name = "REGISTERED_DATE")
    private Date registeredDate;
    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @OneToOne(targetEntity = UserStatus.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "STATUS_ID",referencedColumnName = "STATUS_ID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserStatus userStatus;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "ROLE_USER",
    joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "USER_ID"),
            @JoinColumn(name = "NIC_OR_PASSPORT",referencedColumnName = "NIC_OR_PASSPORT")},
    inverseJoinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")})
    private Set<Role> roles;

}
