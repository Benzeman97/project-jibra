package com.benz.jibra.user.api.exception;

public class UserExistedException extends RuntimeException {

    public UserExistedException(String msg) {
        super(msg);
    }
}
