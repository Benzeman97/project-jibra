package com.benz.authorization.server.api.exception;

public class UserExistedException extends RuntimeException {

    public UserExistedException(String msg) {
        super(msg);
    }
}
