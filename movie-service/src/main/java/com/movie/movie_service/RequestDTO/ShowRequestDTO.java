package com.movie.movie_service.RequestDTO;

import com.movie.movie_service.Helper.ShowStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowRequestDTO {

    @NotNull(message = "Movie ID is required")
    private Long movieId;

    @NotNull(message = "Screen ID is required")
    private Long screenId;

    @NotNull(message = "Show date is required")
    @FutureOrPresent(message = "Show date cannot be in the past")
    private LocalDate showDate;

    @NotNull(message = "Show time is required")
    private LocalTime showTime;

    @Min(value = 1, message = "Available seats must be at least 1")
    private Integer availableSeats;

    @NotNull(message = "Ticket price is required")
    @DecimalMin(value = "0.0", message = "Ticket price must be at least 0")
    @DecimalMax(value = "5000.0", message = "Ticket price cannot exceed 5000")
    private Double ticketPrice;

    @NotNull(message = "Show status is required")
    private ShowStatus status;
}