package com.benz.jibra.user.api.entity;

import com.benz.jibra.user.api.db.Schema;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "USERTABLE", schema = Schema.USERDB, uniqueConstraints = {
        @UniqueConstraint(name = "email", columnNames = "EMAIL")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(UserIdentity.class)
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @SequenceGenerator(name = "USER_ID_GEN", sequenceName = Schema.USERDB + ".USER_ID_SEQ", initialValue = 56001, allocationSize = 1)
    @GeneratedValue(generator = "USER_ID_GEN", strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private long userId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL", nullable = false)
    private String email;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "DOB")
    private Date dob;
    @Column(name = "TELE_NO")
    private String teleNo;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Id
    @Column(name = "NIC_OR_PASSPORT")
    private String nicOrPassport;
    @Column(name = "REGISTERED_DATE")
    private Date registeredDate;
    @Column(name = "MODIFIED_DaTE")
    private Date modifiedDate;

    @OneToOne(targetEntity = UserStatus.class,cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID",referencedColumnName = "STATUS_ID")
    private UserStatus userStatus;

}

