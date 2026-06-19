package com.movie.booking_service.RequestDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSeatRequestDTO {
    @NotNull(message = "Show seat ID cannot be null")
    @Positive(message = "Show seat ID must be a positive number")
    private Long showSeatId;

    @NotBlank(message = "Seat number cannot be blank")
    @Size(min = 1, max = 10, message = "Seat number must be between 1 and 10 characters")
    private String seatNumber;

    @NotNull(message = "Price cannot be null")
    private Double price;
}