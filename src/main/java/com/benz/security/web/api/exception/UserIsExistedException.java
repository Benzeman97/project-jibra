package com.benz.security.web.api.exception;


import org.springframework.security.core.AuthenticationException;

public class UserIsExistedException extends AuthenticationException {

    public UserIsExistedException(String msg)
    {
        super(msg);
    }
}
