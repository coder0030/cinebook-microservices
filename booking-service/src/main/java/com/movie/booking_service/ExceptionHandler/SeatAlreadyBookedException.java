package com.movie.booking_service.ExceptionHandler;

public class SeatAlreadyBookedException extends RuntimeException {
    public SeatAlreadyBookedException(String msg) {
        super(msg);
    }
}
