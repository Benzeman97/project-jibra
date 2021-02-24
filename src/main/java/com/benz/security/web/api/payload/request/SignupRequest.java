package com.benz.security.web.api.payload.request;

import com.benz.security.web.api.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupRequest {

    private String userName;
    private String password;
    private String email;
    private Set<Role> roles;
}
