package com.movie.user_service.RequestDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CancelBookingRequestDTO {

    @NotNull(message = "Booking ID cannot be null")
    private Long bookingId;

    private String cancellationReason;
}