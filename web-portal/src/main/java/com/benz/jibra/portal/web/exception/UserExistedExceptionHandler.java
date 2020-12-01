package com.benz.jibra.portal.web.exception;

import com.benz.jibra.portal.web.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExistedExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> toResponse(UserExistedException ux) {
        ErrorMessage errorMessage = new ErrorMessage(409, ux.getMessage(), "www.benz.com");
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

}
