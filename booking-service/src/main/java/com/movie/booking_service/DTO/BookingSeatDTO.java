package com.movie.booking_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSeatDTO {
    private Long bookingSeatId;
    private Long showSeatId;
    private String seatNumber;
    private Double price;
}
