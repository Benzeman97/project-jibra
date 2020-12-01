package com.benz.jibra.portal.web.exception;

public class UserExistedException extends RuntimeException {

    public UserExistedException(String msg) {
        super(msg);
    }
}
