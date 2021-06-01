package com.benz.authorization.server.api.exception;

import com.benz.authorization.server.api.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExistedExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> toResponse(UserExistedException ex) {
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.CONFLICT.value(), ex.getMessage(), "www.benz.com");
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}
