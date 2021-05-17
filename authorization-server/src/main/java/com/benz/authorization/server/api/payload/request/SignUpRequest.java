package com.benz.authorization.server.api.payload.request;

import com.benz.authorization.server.api.entity.Role;
import com.benz.authorization.server.api.entity.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String dob;
    private String teleNo;
    private String password;
    private String nicOrPassport;
    private Set<Role> roles;
}
