package com.movie.movie_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowSeatDTO {
    private Long id;
    private Boolean isBooked;
    private Double price;
    private LocalDateTime bookingTime;
    private Long showId;
    private Long seatId;
    private String seatLocation;
    private String movieName;
    private String theatreName;
}