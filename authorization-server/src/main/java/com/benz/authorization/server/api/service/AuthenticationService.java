package com.benz.authorization.server.api.service;

import com.benz.authorization.server.api.payload.request.LogInRequest;
import com.benz.authorization.server.api.payload.request.SignUpRequest;
import com.benz.authorization.server.api.payload.response.LogInResponse;
import com.benz.authorization.server.api.payload.response.SignUpResponse;

public interface AuthenticationService {

    SignUpResponse userSignUp(SignUpRequest signUpRequest) throws Exception;

    LogInResponse userLogIn(LogInRequest logInRequest) throws Exception;
}
