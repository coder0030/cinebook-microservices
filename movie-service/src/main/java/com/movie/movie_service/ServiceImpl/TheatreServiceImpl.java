package com.movie.movie_service.ServiceImpl;

import com.movie.movie_service.DTO.TheatreDTO;
import com.movie.movie_service.RequestDTO.TheatreRequestDTO;
import com.movie.movie_service.Service.TheatreService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TheatreServiceImpl implements TheatreService {
    @Override
    public TheatreDTO createTheatre(TheatreRequestDTO requestDTO) {
        return null;
    }

    @Override
    public TheatreDTO getTheatreById(Long id) {
        return null;
    }

    @Override
    public Page<TheatreDTO> getTheatresByCity(String city) {
        return null;
    }

    @Override
    public TheatreDTO updateTheatre(Long id, TheatreRequestDTO requestDTO) {
        return null;
    }

    @Override
    public void deleteTheatre(Long id) {

    }

    @Override
    public Page<TheatreDTO> getAllTheatres(int pageNo, int pageSize) {
        return null;
    }
}
