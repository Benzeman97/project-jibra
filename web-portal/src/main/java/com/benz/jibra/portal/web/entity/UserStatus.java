package com.benz.authorization.server.api.entity;

import com.benz.authorization.server.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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
    private Long active;
    @Column(name = "ACC_NON_EXPIRED")
    private Long accNonExpired;
    @Column(name = "CREDENTIAL_NON_EXPIRED")
    private Long credentialNonExpired;
    @Column(name = "ACC_NON_LOCKED")
    private Long accNonLocked;


}
