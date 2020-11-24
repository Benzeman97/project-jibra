package com.benz.authorization.server.api.entity;

import com.benz.authorization.server.api.db.EPermission;
import com.benz.authorization.server.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "PERMISSION",schema = Schema.SECURITYDB,uniqueConstraints = {
        @UniqueConstraint(name = "name",columnNames = "NAME")
})
@Getter
@Setter
public class Permission {

    @Id
    @Column(name = "ID")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    private EPermission name;

}
