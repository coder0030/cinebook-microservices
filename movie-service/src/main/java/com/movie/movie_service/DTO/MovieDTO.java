package com.movie.movie_service.DTO;

import com.movie.movie_service.Helper.Genre;
import com.movie.movie_service.Helper.Language;
import com.movie.movie_service.Helper.MovieStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String description;
    private Genre genre;
    private Language language;
    private Integer durationMinutes;
    private Double rating;
    private LocalDate releaseDate;
    private String posterUrl;
    private MovieStatus status;
}