package com.movie.movie_service.ExceptionHandler;

public class TheatreAlreadyExistsException extends RuntimeException {
    public TheatreAlreadyExistsException(String msg) {
        super(msg);
    }
}
