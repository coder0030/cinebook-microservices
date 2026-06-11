package com.movie.movie_service.ServiceImpl;

import com.movie.movie_service.DTO.MovieDTO;
import com.movie.movie_service.RequestDTO.MovieRequestDTO;
import com.movie.movie_service.Service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {
    @Override
    public MovieDTO createMovie(MovieRequestDTO requestDTO) {
        return null;
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        return null;
    }

    @Override
    public Page<MovieDTO> getMoviesByGenre(String genre, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public Page<MovieDTO> getAllMovies(int pageNo, int pageSize) {
        return null;
    }

    @Override
    public Page<MovieDTO> getMoviesByLanguage(String language, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public Page<MovieDTO> getMoviesByStatus(String status, int pageNo, int pageSize) {
        return null;
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieRequestDTO requestDTO) {
        return null;
    }

    @Override
    public void deleteMovie(Long id) {

    }
}
