package com.benz.authorization.server.api.exception;

import com.benz.authorization.server.api.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DataNotFoundExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> toResponse(DataNotFoundException dx) {
        ErrorMessage errorMessage = new ErrorMessage(404, dx.getMessage(), "www.benz.com");
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
