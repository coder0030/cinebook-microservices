package com.movie.movie_service.Service;

import com.movie.movie_service.DTO.MovieDTO;
import com.movie.movie_service.RequestDTO.MovieRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;



public interface MovieService {
    MovieDTO createMovie(@Valid MovieRequestDTO requestDTO);

    MovieDTO getMovieById(Long id);
    
    Page<MovieDTO> getMoviesByGenre(String genre, int pageNo, int pageSize);

    Page<MovieDTO> getAllMovies(int pageNo, int pageSize);

    Page<MovieDTO> getMoviesByLanguage(String language, int pageNo, int pageSize);

    Page<MovieDTO> getMoviesByStatus(String status, int pageNo, int pageSize);

    MovieDTO updateMovie(Long id, @Valid MovieRequestDTO requestDTO);

    void deleteMovie(Long id);
}
