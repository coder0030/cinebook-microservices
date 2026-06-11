package com.movie.movie_service.ServiceImpl;

import com.movie.movie_service.DTO.ScreenDTO;
import com.movie.movie_service.RequestDTO.ScreenRequestDTO;
import com.movie.movie_service.Service.ScreenService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ScreeServiceImpl implements ScreenService {
    @Override
    public ScreenDTO createScreen(ScreenRequestDTO requestDTO) {
        return null;
    }

    @Override
    public ScreenDTO getScreenById(Long id) {
        return null;
    }

    @Override
    public void deleteScreen(Long id) {

    }

    @Override
    public ScreenDTO updateScreen(Long id, ScreenRequestDTO requestDTO) {
        return null;
    }

    @Override
    public Page<ScreenDTO> getScreensByTheatre(Long theatreId, int pageNo, int pageSize) {
        return null;
    }
}
