package com.benz.security.web.api.entity;

import com.benz.security.web.api.db.EPermission;
import com.benz.security.web.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "PERMISSION",schema = Schema.TESTDB,uniqueConstraints = {
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
