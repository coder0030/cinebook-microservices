package com.movie.movie_service.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for booking/managing show seats")
public class ShowSeatRequestDTO {

    @NotNull(message = "Show ID is required")
    @Schema(description = "ID of the show", example = "123", required = true, minimum = "1")
    private Long showId;

    @NotNull(message = "Seat ID is required")
    @Schema(description = "ID of the seat being booked", example = "456", required = true, minimum = "1")
    private Long seatId;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @DecimalMin(value = "50.0", message = "Minimum ticket price is 50")
    @DecimalMax(value = "5000.0", message = "Maximum ticket price is 5000")
    @Schema(description = "Ticket price for this seat", example = "350.0", required = true, minimum = "50.0", maximum = "5000.0")
    private Double price;

    @Schema(description = "Whether the seat is booked", example = "false", defaultValue = "false")
    private Boolean isBooked = false;

    @Schema(description = "Time when seat was booked or put on hold", example = "2024-12-20T10:30:00")
    private LocalDateTime bookingTime;
}