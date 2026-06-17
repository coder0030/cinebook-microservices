package com.movie.movie_service.Mapper;

import com.movie.movie_service.DTO.ShowSeatDTO;
import com.movie.movie_service.Entity.ShowSeat;
import com.movie.movie_service.RequestDTO.ShowSeatRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ShowSeatMapper {

    public ShowSeat convertRequestDTOToEntity(ShowSeatRequestDTO requestDTO, ShowSeat showSeat) {
        showSeat.setIsBooked(requestDTO.getIsBooked());
        showSeat.setPrice(requestDTO.getPrice());
        showSeat.setBookingTime(requestDTO.getBookingTime());
        return showSeat;
    }

    public ShowSeatDTO convertEntityToDTO(ShowSeat showSeat) {
        return ShowSeatDTO.builder()
                .id(showSeat.getId())
                .isBooked(showSeat.getIsBooked())
                .price(showSeat.getPrice())
                .bookingTime(showSeat.getBookingTime())
                .showId(showSeat.getShow() != null ? showSeat.getShow().getId() : null)
                .seatId(showSeat.getSeat() != null ? showSeat.getSeat().getId() : null)
                .seatLocation(showSeat.getSeat() != null ?
                        showSeat.getSeat().getRowLabel() + showSeat.getSeat().getSeatNumber() : null)
                .movieName(showSeat.getShow() != null && showSeat.getShow().getMovie() != null ?
                        showSeat.getShow().getMovie().getTitle() : null)
                .theatreName(showSeat.getShow() != null && showSeat.getShow().getTheatre() != null ?
                        showSeat.getShow().getTheatre().getName() : null)
                .build();
    }
}