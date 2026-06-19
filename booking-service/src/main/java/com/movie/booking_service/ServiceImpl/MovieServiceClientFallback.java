package com.movie.booking_service.ServiceImpl;

import com.movie.booking_service.ExceptionHandler.ServiceUnavailableException;
import com.movie.booking_service.Service.MovieServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MovieServiceClientFallback implements MovieServiceClient {

    @Override
    public boolean checkShowsExists(Long showId) {
        log.warn("Circuit breaker fallback for checkShowsExists with showId: {}", showId);
        throw new ServiceUnavailableException("Movie service is currently unavailable");
    }

    @Override
    public boolean checkShowsAndSeatBooked(Long seatId, Long showId) {
        log.warn("Circuit breaker fallback for checkShowsAndSeatBooked with seatId: {}, showId: {}", seatId, showId);
        throw new ServiceUnavailableException("Movie service is currently unavailable");
    }

    @Override
    public Double getSeatPrice(Long seatId, Long showId) {
        log.warn("Circuit breaker fallback for getSeatPrice with seatId: {}, showId: {}", seatId, showId);
        throw new ServiceUnavailableException("Movie service is currently unavailable");
    }
}