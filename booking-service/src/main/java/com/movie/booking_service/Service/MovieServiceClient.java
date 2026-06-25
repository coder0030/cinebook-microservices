package com.movie.booking_service.Service;

import com.movie.booking_service.ServiceImpl.MovieServiceClientFallback;
import lombok.Getter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "movie-service",
        fallback = MovieServiceClientFallback.class
)
public interface MovieServiceClient {

    @GetMapping("/api/v1/public/movies/shows/{showId}")
    boolean checkShowsExists(@PathVariable("showId") Long showId);

    @GetMapping("/api/v1/movies/public/showseats/{seatId}/shows/{showId}/booked")
    boolean checkShowsAndSeatBooked(@PathVariable("seatId") Long seatId,
                                    @PathVariable("showId") Long showId);

    @GetMapping("/api/v1/movies/public/showseats/{seatId}/shows/{showId}/price")
    Double getSeatPrice(@PathVariable("seatId") Long seatId,
                        @PathVariable("showId") Long showId);
}