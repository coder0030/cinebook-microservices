package com.movie.movie_service.DTO;

import com.movie.movie_service.Helper.SeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {
    private Long id;
    private String seatNumber;
    private String rowLabel;
    private SeatType seatType;
    private Boolean isBooked;
    private Double price;
    private Long showId;
}