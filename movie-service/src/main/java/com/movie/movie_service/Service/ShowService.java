package com.movie.movie_service.Service;

import com.movie.movie_service.DTO.ShowDTO;
import com.movie.movie_service.RequestDTO.ShowRequestDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


public interface ShowService {
    ShowDTO createShow(@Valid ShowRequestDTO requestDTO);

    ShowDTO getShowById(Long id);

    ShowDTO updateShow(Long id, @Valid ShowRequestDTO requestDTO);

    void deleteShow(Long id);

    List<ShowDTO> getShowsByMovie(Long movieId);

    List<ShowDTO> getShowsByTheatre(Long theatreId, int pageNo, int pageSize);

    List<ShowDTO> getShowsByScreenAndDate(Long screenId, LocalDate date, int pageNo, int pageSize);

    List<ShowDTO> getShowsByDate(LocalDate date, int pageNo, int pageSize);
}
