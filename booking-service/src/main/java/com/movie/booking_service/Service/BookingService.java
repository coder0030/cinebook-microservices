package com.movie.booking_service.Service;

import com.movie.booking_service.DTO.BookingResponseDTO;
import com.movie.booking_service.Helper.BookingStatus;
import com.movie.booking_service.RequestDTO.BookingRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

public interface BookingService {
    BookingResponseDTO createBooking(@Valid BookingRequestDTO requestDTO);

    BookingResponseDTO getBookingById(Long bookingId);

    Page<BookingResponseDTO> getBookingsByUserId(Long userId, int pageNo, int pageSize);

    Page<BookingResponseDTO> getBookingsByShowId(Long showId, int pageNo, int pageSize);

    BookingResponseDTO updateBookingStatus(Long bookingId, BookingStatus status);

    void cancelBooking(Long bookingId);

    BookingResponseDTO confirmBooking(Long bookingId);

    Page<BookingResponseDTO> getActiveBookings(int pageNo, int pageSize);
}
