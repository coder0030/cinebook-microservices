package com.movie.movie_service.ServiceImpl;

import com.movie.movie_service.DTO.ShowDTO;
import com.movie.movie_service.RequestDTO.ShowRequestDTO;
import com.movie.movie_service.Service.ShowService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShowServiceImpl implements ShowService {
    @Override
    public ShowDTO createShow(ShowRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ShowDTO getShowById(Long id) {
        return null;
    }

    @Override
    public ShowDTO updateShow(Long id, ShowRequestDTO requestDTO) {
        return null;
    }

    @Override
    public void deleteShow(Long id) {

    }

    @Override
    public List<ShowDTO> getShowsByMovie(Long movieId) {
        return List.of();
    }

    @Override
    public List<ShowDTO> getShowsByTheatre(Long theatreId, int pageNo, int pageSize) {
        return List.of();
    }

    @Override
    public List<ShowDTO> getShowsByScreenAndDate(Long screenId, LocalDate date, int pageNo, int pageSize) {
        return List.of();
    }

    @Override
    public List<ShowDTO> getShowsByDate(LocalDate date, int pageNo, int pageSize) {
        return List.of();
    }
}
