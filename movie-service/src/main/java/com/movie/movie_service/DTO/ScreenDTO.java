package com.movie.movie_service.DTO;

import com.movie.movie_service.Helper.ScreenType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenDTO {
    private Long id;
    private String screenName;
    private Integer totalSeats;
    private ScreenType screenType;
    private Long theatreId;
    private String theatreName;
    private Set<ShowDTO> shows;
    private Integer totalShows;

}