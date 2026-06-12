package com.movie.user_service.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for cancelling a booking")
public class CancelBookingRequestDTO {

    @NotNull(message = "Booking ID cannot be null")
    @Schema(description = "ID of the booking to cancel", example = "12345", required = true)
    private Long bookingId;

    @Schema(description = "Reason for cancellation", example = "Changed plans", required = false, maxLength = 255)
    private String cancellationReason;
}