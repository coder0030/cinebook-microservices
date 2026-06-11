package com.movie.movie_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreDTO {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String state;
    private Integer totalScreens;
    private List<ScreenDTO> screens;
}