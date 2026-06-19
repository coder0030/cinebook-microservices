package com.movie.booking_service.RequestDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequestDTO {
    @NotNull(message = "User ID cannot be null")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    @NotNull(message = "Show ID cannot be null")
    @Positive(message = "Show ID must be a positive number")
    private Long showId;

    @NotEmpty(message = "At least one seat must be selected")
    @Size(min = 1, max = 10, message = "You can book between 1 and 10 seats at a time")
    @Valid
    private List<@NotNull(message = "Seat ID cannot be null")
    @Positive(message = "Seat ID must be a positive number")
            Long> showSeatIds;
}