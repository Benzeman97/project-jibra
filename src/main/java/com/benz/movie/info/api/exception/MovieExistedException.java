package com.benz.movie.info.api.exception;

public class MovieExistedException extends RuntimeException{

    public MovieExistedException(String msg)
    {
        super(msg);
    }
}
