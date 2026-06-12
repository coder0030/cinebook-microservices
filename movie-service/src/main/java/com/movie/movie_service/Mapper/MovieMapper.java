package com.movie.movie_service.Mapper;

import com.movie.movie_service.DTO.MovieDTO;
import com.movie.movie_service.Entity.Movie;
import com.movie.movie_service.RequestDTO.MovieRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie convertRequestDTOToEntity(MovieRequestDTO requestDTO, Movie movie) {
        movie.setTitle(requestDTO.getTitle());
        movie.setDescription(requestDTO.getDescription());
        movie.setGenre(requestDTO.getGenre());
        movie.setLanguage(requestDTO.getLanguage());
        movie.setDurationMinutes(requestDTO.getDurationMinutes());
        movie.setReleaseDate(requestDTO.getReleaseDate());
        movie.setRating(requestDTO.getRating());
        return movie;
    }

    public MovieDTO convertEntityToDTO(Movie movie) {
        return MovieDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .genre(movie.getGenre())
                .language(movie.getLanguage())
                .durationMinutes(movie.getDurationMinutes())
                .releaseDate(movie.getReleaseDate())
                .director(movie.getDirector())
                .rating(movie.getRating())
                .build();
    }
}