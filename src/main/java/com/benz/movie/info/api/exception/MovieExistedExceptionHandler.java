package com.benz.movie.info.api.exception;

import com.benz.movie.info.api.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MovieExistedExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> toResponse(MovieExistedException ex) {
        ErrorMessage errorMessage = new ErrorMessage(409, ex.getMessage(), "www.benz.com");
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
}
