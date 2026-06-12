package com.movie.movie_service.ExceptionHandler;

public class TheatreNotFoundException extends RuntimeException {
    public TheatreNotFoundException(String msg) {
        super(msg);
    }
}
