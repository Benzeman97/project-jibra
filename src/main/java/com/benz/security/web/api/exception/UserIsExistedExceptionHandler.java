package com.benz.security.web.api.exception;

import com.benz.security.web.api.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserIsExistedExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> toResponse(UserIsExistedException ex)
    {
        ErrorMessage errorMessage=new ErrorMessage(409,ex.getMessage(),"www.benz.com");
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}
