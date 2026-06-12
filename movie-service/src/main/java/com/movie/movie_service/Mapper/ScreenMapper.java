package com.movie.movie_service.Mapper;

import com.movie.movie_service.DTO.ScreenDTO;
import com.movie.movie_service.Entity.Screen;
import com.movie.movie_service.RequestDTO.ScreenRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ScreenMapper {

    public Screen convertRequestDTOToEntity(ScreenRequestDTO requestDTO, Screen screen) {
        screen.setScreenName(requestDTO.getScreenName());
        screen.setTotalSeats(requestDTO.getTotalSeats());
        screen.setScreenType(requestDTO.getScreenType());
        return screen;
    }

    public ScreenDTO convertEntityToDTO(Screen screen) {
        return ScreenDTO.builder()
                .id(screen.getId())
                .screenName(screen.getScreenName())
                .totalSeats(screen.getTotalSeats())
                .screenType(screen.getScreenType())
                .theatreId(screen.getTheatre() != null ? screen.getTheatre().getId() : null)
                .theatreName(screen.getTheatre() != null ? screen.getTheatre().getName() : null)
                .totalShows(screen.getShowsList() != null ? screen.getShowsList().size() : 0)
                .build();
    }
}