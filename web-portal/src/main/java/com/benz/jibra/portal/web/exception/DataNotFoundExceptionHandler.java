package com.benz.jibra.portal.web.exception;

import com.benz.jibra.portal.web.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DataNotFoundExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> toResponse(DataNotFoundException dx)
    {
        ErrorMessage errorMessage=new ErrorMessage(HttpStatus.NOT_FOUND.value(),dx.getMessage(),"www.benz.com");
        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }
}
