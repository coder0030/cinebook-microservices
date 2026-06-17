package com.movie.movie_service.Service;

import com.movie.movie_service.DTO.SeatDTO;
import com.movie.movie_service.RequestDTO.SeatRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;


public interface SeatService {
    SeatDTO createSeat(@Valid SeatRequestDTO requestDTO);

    SeatDTO getSeatById(Long id);

    SeatDTO updateSeat(Long id, @Valid SeatRequestDTO requestDTO);

    SeatDTO bookSeat(Long id);

    SeatDTO cancelSeatBooking(Long id);

    void deleteSeat(Long id);

    Page<SeatDTO> createMultipleSeats(@Valid List<SeatRequestDTO> requestDTOs);

    Page<SeatDTO> getSeatsByShow(Long showId, int pageNo, int pageSize);

    List<SeatDTO> getAvailableSeatsByShow(Long showId);
}
