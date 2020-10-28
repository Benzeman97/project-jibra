package com.benz.user.api.entity;

import com.benz.user.api.db.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "USERTABLE",schema = Schema.USERDB,uniqueConstraints = {
        @UniqueConstraint(name = "email",columnNames = "EMAIL")
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(UserIdentity.class)
public class User {

    @Id
    @SequenceGenerator(name = "USER_ID_GEN",sequenceName = Schema.USERDB+".USER_ID_SEQ",initialValue = 56001,allocationSize = 1)
    @GeneratedValue(generator = "USER_ID_GEN",strategy = GenerationType.SEQUENCE)
    @Column(name = "USER_ID")
    private long userId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL",nullable = false)
    private String email;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "DOB")
    private LocalDate dob;
    @Column(name = "TELE_NO")
    private String teleNo;
    @Column(name = "PASSWORD",nullable = false)
    private String password;
    @Id
    @Column(name = "NIC_OR_PASSPORT")
    private String nicOrPassport;
    @Column(name = "REGISTERED_DATE")
    private Date registeredDate;

}
