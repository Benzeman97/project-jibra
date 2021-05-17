package com.benz.authorization.server.api.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LogInRequest {

    private String username;
    private String password;
    private boolean status;
}
