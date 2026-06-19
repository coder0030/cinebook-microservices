package com.movie.booking_service.Mapper;

import com.movie.booking_service.DTO.BookingSeatDTO;
import com.movie.booking_service.Entity.BookingSeat;
import org.springframework.stereotype.Component;

@Component
public class BookingSeatMapper {
    public BookingSeatDTO convertToBookingSeatDTO(BookingSeat bookingSeat) {
        BookingSeatDTO dto = new BookingSeatDTO();
        dto.setBookingSeatId(bookingSeat.getBookingSeatId());
        dto.setShowSeatId(bookingSeat.getShowSeatId());
        dto.setSeatNumber(bookingSeat.getSeatNumber());
        dto.setPrice(bookingSeat.getPrice());
        return dto;
    }

//    public BookingSeat convertRequestDTOToEntity(BookingSeatRequestDTO requestDTO) {
//        if (requestDTO == null) {
//            return null;
//        }
//
//        BookingSeat bookingSeat = new BookingSeat();
//        bookingSeat.setShowSeatId(requestDTO.getShowSeatId());
//        bookingSeat.setSeatNumber(requestDTO.getSeatNumber());
//        bookingSeat.setPrice(requestDTO.getPrice());
//        // Note: bookingSeatId and booking reference are usually set by the service layer
//
//        return bookingSeat;
//    }
}
