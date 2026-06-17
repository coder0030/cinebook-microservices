package com.movie.movie_service.Service;

import com.movie.movie_service.DTO.ShowSeatDTO;
import org.springframework.data.domain.Page;

public interface ShowSeatService {
    ShowSeatDTO getShowSeatById(Long id);

    Page<ShowSeatDTO> getShowSeatsByShow(Long showId, int pageNo, int pageSize);

    Page<ShowSeatDTO> getAvailableShowSeatsByShow(Long showId, int pageNo, int pageSize);

    ShowSeatDTO updateShowSeatPrice(Long id, Double price);

    void deleteShowSeat(Long id);
}
