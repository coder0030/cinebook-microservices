package com.movie.movie_service.Mapper;

import com.movie.movie_service.DTO.SeatDTO;
import com.movie.movie_service.Entity.Seat;
import com.movie.movie_service.RequestDTO.SeatRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public Seat convertRequestDTOToEntity(SeatRequestDTO requestDTO, Seat seat) {
        seat.setSeatNumber(requestDTO.getSeatNumber());
        seat.setRowLabel(requestDTO.getRowLabel());
        seat.setSeatType(requestDTO.getSeatType());
        seat.setIsBooked(requestDTO.getIsBooked() != null ? requestDTO.getIsBooked() : false);
        return seat;
    }

    public SeatDTO convertEntityToDTO(Seat seat) {
        return SeatDTO.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .rowLabel(seat.getRowLabel())
                .seatType(seat.getSeatType())
                .isBooked(seat.getIsBooked())
                .screenId(seat.getScreen() != null ? seat.getScreen().getId() : null)
                .screenName(seat.getScreen() != null ? seat.getScreen().getScreenName() : null)
                .theatreId(seat.getScreen() != null && seat.getScreen().getTheatre() != null ?
                        seat.getScreen().getTheatre().getId() : null)
                .build();
    }
}