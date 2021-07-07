package com.benz.movie.info.api.exception;

import com.benz.movie.info.api.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DataNotFoundExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> toResponse(DataNotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage(404, ex.getMessage(), "www.benz.com");
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
