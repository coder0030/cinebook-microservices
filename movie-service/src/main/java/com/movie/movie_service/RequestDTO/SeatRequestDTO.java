package com.movie.movie_service.RequestDTO;

import com.movie.movie_service.Helper.SeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request object for creating/updating a seat in a screen")
public class SeatRequestDTO {

    @NotBlank(message = "Seat number is required")
    @Schema(description = "Seat number within the row", example = "1", required = true, minLength = 1, maxLength = 3)
    private String seatNumber;

    @NotBlank(message = "Row label is required")
    @Pattern(regexp = "^[A-Z]$", message = "Row label must be a single uppercase letter")
    @Schema(description = "Row label (single uppercase letter)", example = "A", required = true, pattern = "^[A-Z]$")
    private String rowLabel;

    @NotNull(message = "Seat type is required")
    @Schema(description = "Type/category of the seat", required = true,
            example = "NORMAL", allowableValues = {"NORMAL", "VIP", "RECLINER", "HANDICAP"})
    private SeatType seatType;

    @NotNull(message = "Booking status is required")
    @Schema(description = "Current booking status of the seat", example = "false", required = true)
    private Boolean isBooked;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", message = "Price must be at least 0")
    @DecimalMax(value = "9999.99", message = "Price cannot exceed 9999.99")
    @Schema(description = "Base price for this seat", example = "250.0", required = true, minimum = "0.0", maximum = "9999.99")
    private Double price;

    @NotNull(message = "Show ID is required")
    @Min(value = 1, message = "Show ID must be at least 1")
    @Schema(description = "ID of the show this seat belongs to", example = "123", required = true, minimum = "1")
    private Long showId;

    @NotNull(message = "Screen ID is required")
    @Min(value = 1, message = "Screen ID must be at least 1")
    @Schema(description = "ID of the screen this seat belongs to", example = "456", required = true, minimum = "1")
    private Long screenId;
}