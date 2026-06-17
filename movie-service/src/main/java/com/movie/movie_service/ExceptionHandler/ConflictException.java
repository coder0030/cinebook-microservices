package com.movie.movie_service.ExceptionHandler;

public class ConflictException extends RuntimeException{
    public ConflictException(String msg) {
        super(msg);
    }
}
