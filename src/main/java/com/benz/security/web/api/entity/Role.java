package com.benz.security.web.api.entity;

import com.benz.security.web.api.db.ERole;
import com.benz.security.web.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ROLE",schema = Schema.TESTDB,uniqueConstraints = {
        @UniqueConstraint(name = "name",columnNames = "NAME")
})
@Getter
@Setter
public class Role {

    @Id
    @Column(name = "ID")
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    private ERole name;

    // bi-directional
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<User> users;

    // uni-directional
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "PERMISSION_ROLE",joinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")},
    inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID",referencedColumnName = "ID")})
    private Set<Permission> permissions;
}
