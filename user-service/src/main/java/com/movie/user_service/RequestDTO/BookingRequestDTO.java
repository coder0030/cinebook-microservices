package com.movie.user_service.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for booking movie tickets")
public class BookingRequestDTO {

    @NotNull(message = "User ID cannot be null")
    @Schema(description = "ID of the user making the booking", example = "12", required = true, minimum = "1")
    private Long userId;

    @NotNull(message = "Movie ID cannot be null")
    @Schema(description = "ID of the movie being booked", example = "5", required = true, minimum = "1")
    private Long movieId;

    @NotNull(message = "Theater name cannot be null")
    @Schema(description = "Name of the theater", example = "PVR Cinemas", required = true)
    private String theaterName;

    @NotNull(message = "Show time cannot be null")
    @Schema(description = "Date and time of the show", example = "2024-12-25T15:30:00", required = true)
    private LocalDateTime showTime;

    @NotNull(message = "Number of seats cannot be null")
    @Min(value = 1, message = "Number of seats must be at least 1")
    @Schema(description = "Number of seats to book", example = "2", required = true, minimum = "1", maximum = "10")
    private Integer numberOfSeats;
}