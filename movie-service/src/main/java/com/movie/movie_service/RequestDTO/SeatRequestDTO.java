package com.movie.movie_service.RequestDTO;

import com.movie.movie_service.Helper.SeatType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequestDTO {

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @NotBlank(message = "Row label is required")
    @Pattern(regexp = "^[A-Z]$", message = "Row label must be a single uppercase letter")
    private String rowLabel;

    @NotNull(message = "Seat type is required")
    private SeatType seatType;

    @NotNull(message = "Booking status is required")
    private Boolean isBooked;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be at least 0")
    private Double price;

    @NotNull(message = "Show ID is required")
    private Long showId;
}