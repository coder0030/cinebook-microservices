package com.movie.booking_service.Mapper;

import com.movie.booking_service.DTO.BookingResponseDTO;
import com.movie.booking_service.DTO.BookingSeatDTO;
import com.movie.booking_service.Entity.Booking;
import com.movie.booking_service.Helper.BookingStatus;
import com.movie.booking_service.RequestDTO.BookingRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final BookingSeatMapper bookingSeatMapper;

    public Booking convertRequestDTOToEntity(BookingRequestDTO requestDTO,
                                      Booking booking) {
        booking.setUserId(requestDTO.getUserId());
        booking.setShowId(requestDTO.getShowId());
        booking.setStatus(BookingStatus.PENDING);
        booking.setBookingTime(LocalDateTime.now());
        booking.setExpiresAt(LocalDateTime.now().plusHours(5));

        return booking;
    }

    public BookingResponseDTO convertEntityToDTO(Booking booking) {
        BookingResponseDTO responseDTO = new BookingResponseDTO();
        responseDTO.setBookingId(booking.getId());
        responseDTO.setUserId(booking.getUserId());
        responseDTO.setShowId(booking.getShowId());
        responseDTO.setStatus(booking.getStatus());
        responseDTO.setTotalAmount(booking.getTotalAmount());
        responseDTO.setBookingTime(booking.getBookingTime());
        responseDTO.setExpiresAt(booking.getExpiresAt());

        if(booking.getBookingSeats() != null) {
            List<BookingSeatDTO> seatDTOs = booking.getBookingSeats()
                    .stream()
                    .map(bookingSeatMapper::convertToBookingSeatDTO)
                    .collect(Collectors.toList());
            responseDTO.setBookingSeats(seatDTOs);
        }
        return responseDTO;
    }
}
