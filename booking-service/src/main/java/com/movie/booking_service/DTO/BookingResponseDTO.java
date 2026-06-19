package com.movie.booking_service.DTO;

import com.movie.booking_service.Helper.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponseDTO {
    private Long bookingId;
    private Long userId;
    private Long showId;
    private BookingStatus status;
    private Double totalAmount;
    private LocalDateTime bookingTime;
    private LocalDateTime expiresAt;
    private List<BookingSeatDTO> bookingSeats;
    private String errorMessage;
    private String timestamp;
}
