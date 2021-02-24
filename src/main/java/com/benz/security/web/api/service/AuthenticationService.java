package com.benz.security.web.api.service;

import com.benz.security.web.api.payload.request.LoginRequest;
import com.benz.security.web.api.payload.request.SignupRequest;
import com.benz.security.web.api.payload.response.Response;

public interface AuthenticationService {

     Response userLogin(LoginRequest login);
     Response userRegistration(SignupRequest signup);

}
