package com.movie.movie_service.Mapper;

import com.movie.movie_service.DTO.TheatreDTO;
import com.movie.movie_service.Entity.Theatre;
import com.movie.movie_service.RequestDTO.TheatreRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class TheatreMapper {

    public Theatre convertRequestDTOToEntity(TheatreRequestDTO requestDTO, Theatre theatre) {
        theatre.setName(requestDTO.getName());
        theatre.setAddress(requestDTO.getAddress());
        theatre.setCity(requestDTO.getCity());
        theatre.setState(requestDTO.getState());
        theatre.setTotalScreens(requestDTO.getTotalScreens());
        return theatre;
    }

    public TheatreDTO convertEntityToDTO(Theatre theatre) {
        return TheatreDTO.builder()
                .id(theatre.getId())
                .name(theatre.getName())
                .address(theatre.getAddress())
                .city(theatre.getCity())
                .state(theatre.getState())
                .totalScreens(theatre.getTotalScreens())
                .build();
    }
}