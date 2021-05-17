package com.benz.authorization.server.api.entity;

import com.benz.authorization.server.api.db.ERole;
import com.benz.authorization.server.api.db.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ROLE",schema = Schema.SECURITYDB)
@Getter
@Setter
public class Role {

    @Id
    @Column(name = "ID")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    private ERole name;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name = "PERMISSION_ROLE",
    joinColumns={@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")},
    inverseJoinColumns = {@JoinColumn(name = "PERMISSION_ID",referencedColumnName = "ID")})
    private Set<Permission> permissions;
}
