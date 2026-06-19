package com.movie.booking_service.ExceptionHandler;

public class DuplicateBookingException extends RuntimeException {
    public DuplicateBookingException(String msg) {
        super(msg);
    }
}
