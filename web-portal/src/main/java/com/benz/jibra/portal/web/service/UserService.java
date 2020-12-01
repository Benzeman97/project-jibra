package com.benz.jibra.portal.web.service;

import com.benz.jibra.portal.web.payload.request.LoginRequest;
import com.benz.jibra.portal.web.payload.request.SignUpRequest;
import com.benz.jibra.portal.web.payload.response.UserResponse;

public interface UserService {

    UserResponse signUpUser(SignUpRequest signUpRequest) throws Exception;

    UserResponse loginUser(LoginRequest loginRequest) throws Exception;

}
