package com.benz.jibra.portal.web.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignUpRequest {

    private String nicOrPassport;
    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private Date dob;
    private String teleNo;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
}
