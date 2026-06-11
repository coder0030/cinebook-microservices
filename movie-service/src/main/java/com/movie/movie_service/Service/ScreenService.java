package com.movie.movie_service.Service;

import com.movie.movie_service.DTO.ScreenDTO;
import com.movie.movie_service.RequestDTO.ScreenRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


public interface ScreenService {
    ScreenDTO createScreen(@Valid ScreenRequestDTO requestDTO);

    ScreenDTO getScreenById(Long id);

    void deleteScreen(Long id);

    ScreenDTO updateScreen(Long id, @Valid ScreenRequestDTO requestDTO);

    Page<ScreenDTO> getScreensByTheatre(Long theatreId, int pageNo, int pageSize);
}
