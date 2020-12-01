package com.benz.jibra.user.api.entity;

import com.benz.jibra.user.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "USER_STATUS", schema = Schema.SECURITYDB)
@Getter
@Setter
public class UserStatus {

    @Id
    @SequenceGenerator(name = "STATUS_ID_GEN",sequenceName = Schema.SECURITYDB+".STATUS_ID_SEQ",initialValue = 6001,allocationSize = 1)
    @GeneratedValue(generator = "STATUS_ID_GEN",strategy = GenerationType.SEQUENCE)
    @Column(name = "STATUS_ID")
    private long statusId;
    @Column(name = "ACTIVE")
    private long active;
    @Column(name = "ACC_NON_EXPIRED")
    private long accNonExpired;
    @Column(name = "CREDENTIAL_NON_EXPIRED")
    private long credentialNonExpired;
    @Column(name = "ACC_NON_LOCKED")
    private long accNonLocked;


}
