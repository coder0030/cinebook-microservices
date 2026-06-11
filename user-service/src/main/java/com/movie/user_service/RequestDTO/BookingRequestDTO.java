package com.movie.user_service.RequestDTO;

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
public class BookingRequestDTO {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Movie ID cannot be null")
    private Long movieId;

    @NotNull(message = "Theater name cannot be null")
    private String theaterName;

    @NotNull(message = "Show time cannot be null")
    private LocalDateTime showTime;

    @NotNull(message = "Number of seats cannot be null")
    @Min(value = 1, message = "Number of seats must be at least 1")
    private Integer numberOfSeats;
}