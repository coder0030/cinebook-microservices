package com.movie.movie_service.Service;

import com.movie.movie_service.DTO.TheatreDTO;
import com.movie.movie_service.RequestDTO.TheatreRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;


public interface TheatreService {
    TheatreDTO createTheatre(@Valid TheatreRequestDTO requestDTO);

    TheatreDTO getTheatreById(Long id);
    
    Page<TheatreDTO> getTheatresByCity(String city);

    TheatreDTO updateTheatre(Long id, @Valid TheatreRequestDTO requestDTO);

    void deleteTheatre(Long id);

    Page<TheatreDTO> getAllTheatres(int pageNo, int pageSize);
}
