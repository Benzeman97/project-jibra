package com.benz.authorization.server.api.entity;


import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserIdentity implements Serializable {
    private long userId;
    private String nicOrPassport;

}
