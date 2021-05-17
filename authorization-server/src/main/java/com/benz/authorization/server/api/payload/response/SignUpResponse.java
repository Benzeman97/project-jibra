package com.benz.authorization.server.api.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpResponse {

    private String userName;
    private String accessToken;
    private boolean status;
}
