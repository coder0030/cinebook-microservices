package com.movie.movie_service.ServiceImpl;

import com.movie.movie_service.DTO.SeatDTO;
import com.movie.movie_service.RequestDTO.SeatRequestDTO;
import com.movie.movie_service.Service.SeatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    @Override
    public SeatDTO createSeat(SeatRequestDTO requestDTO) {
        return null;
    }

    @Override
    public SeatDTO getSeatById(Long id) {
        return null;
    }

    @Override
    public SeatDTO updateSeat(Long id, SeatRequestDTO requestDTO) {
        return null;
    }

    @Override
    public SeatDTO bookSeat(Long id) {
        return null;
    }

    @Override
    public SeatDTO cancelSeatBooking(Long id) {
        return null;
    }

    @Override
    public void deleteSeat(Long id) {

    }

    @Override
    public List<SeatDTO> createMultipleSeats(List<SeatRequestDTO> requestDTOs) {
        return List.of();
    }

    @Override
    public List<SeatDTO> getSeatsByShow(Long showId, int pageNo, int pageSize) {
        return List.of();
    }

    @Override
    public List<SeatDTO> getAvailableSeatsByShow(Long showId, int pageNo, int pageSize) {
        return List.of();
    }
}
