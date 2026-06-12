package com.movie.movie_service.RequestDTO;

import com.movie.movie_service.Helper.ShowStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request object for scheduling/updating a movie show")
public class ShowRequestDTO {

    @NotNull(message = "Movie ID is required")
    @Schema(description = "ID of the movie being screened", example = "10", required = true, minimum = "1")
    private Long movieId;

    @NotNull(message = "Screen ID is required")
    @Schema(description = "ID of the screen where show will be played", example = "25", required = true, minimum = "1")
    private Long screenId;

    @NotNull(message = "Show date is required")
    @FutureOrPresent(message = "Show date cannot be in the past")
    @Schema(description = "Date of the show (cannot be past date)", example = "2024-12-25", required = true)
    private LocalDate showDate;

    @NotNull(message = "Show time is required")
    @Schema(description = "Start time of the show", example = "15:30:00", required = true)
    private LocalTime showTime;

    @NotNull(message = "End time is required")
    @Schema(description = "End time of the show (must be after showTime)", example = "18:00:00", required = true)
    private LocalTime endTime;

    @Min(value = 1, message = "Available seats must be at least 1")
    @Schema(description = "Number of seats available for booking", example = "120", required = true, minimum = "1")
    private Integer availableSeats;

    @NotNull(message = "Ticket price is required")
    @DecimalMin(value = "0.0", message = "Ticket price must be at least 0")
    @DecimalMax(value = "5000.0", message = "Ticket price cannot exceed 5000")
    @Schema(description = "Base ticket price", example = "350.0", required = true, minimum = "0.0", maximum = "5000.0")
    private Double ticketPrice;

    @NotNull(message = "Show status is required")
    @Schema(description = "Current status of the show", required = true,
            example = "SCHEDULED", allowableValues = {"SCHEDULED", "RUNNING", "COMPLETED", "CANCELLED"})
    private ShowStatus status;
}