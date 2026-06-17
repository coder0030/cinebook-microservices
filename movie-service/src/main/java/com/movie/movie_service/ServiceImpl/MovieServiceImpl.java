package com.movie.movie_service.ServiceImpl;

import com.movie.movie_service.DTO.MovieDTO;
import com.movie.movie_service.Entity.Movie;
import com.movie.movie_service.Helper.Genre;
import com.movie.movie_service.Helper.Language;
import com.movie.movie_service.Helper.MovieStatus;
import com.movie.movie_service.Mapper.MovieMapper;
import com.movie.movie_service.Repository.MovieRepository;
import com.movie.movie_service.RequestDTO.MovieRequestDTO;
import com.movie.movie_service.Service.MovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    @Transactional
    public MovieDTO createMovie(MovieRequestDTO requestDTO) {
        log.info("Creating new movie: {}", requestDTO.getTitle());

        if (movieRepository.existsByTitleAndGenreAndLanguageAndIsActiveTrue(
                requestDTO.getTitle(),
                requestDTO.getGenre(),
                requestDTO.getLanguage())) {
            throw new RuntimeException("Movie already exists with title: " + requestDTO.getTitle() +
                    ", genre: " + requestDTO.getGenre() +
                    ", language: " + requestDTO.getLanguage());
        }

        Movie movie = movieMapper.convertRequestDTOToEntity(requestDTO, new Movie());
        Movie savedMovie = movieRepository.save(movie);
        log.info("Movie created successfully with ID: {}", savedMovie.getId());

        return movieMapper.convertEntityToDTO(savedMovie);
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        log.info("Fetching movie by ID: {}", id);

        Movie movie = movieRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));

        return movieMapper.convertEntityToDTO(movie);
    }

    @Override
    public Page<MovieDTO> getMoviesByGenre(String genre, int pageNo, int pageSize) {
        log.info("Fetching movies by genre: {}, page: {}, size: {}", genre, pageNo, pageSize);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("title").ascending());

        try {
            Genre genreEnum = Genre.valueOf(genre.toUpperCase());
            Page<Movie> moviePage = movieRepository.findByGenreAndIsActiveTrue(genreEnum, pageable);
            return moviePage.map(movieMapper::convertEntityToDTO);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid genre: " + genre);
        }
    }

    @Override
    public Page<MovieDTO> getAllMovies(int pageNo, int pageSize) {
        log.info("Fetching all movies - page: {}, size: {}", pageNo, pageSize);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("releaseDate").descending());
        Page<Movie> moviePage = movieRepository.findByIsActiveTrue(pageable);

        return moviePage.map(movieMapper::convertEntityToDTO);
    }

    @Override
    public Page<MovieDTO> getMoviesByLanguage(String language, int pageNo, int pageSize) {
        log.info("Fetching movies by language: {}, page: {}, size: {}", language, pageNo, pageSize);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("title").ascending());

        try {
            Language languageEnum = Language.valueOf(language.toUpperCase());
            Page<Movie> moviePage = movieRepository.findByLanguageAndIsActiveTrue(languageEnum, pageable);
            return moviePage.map(movieMapper::convertEntityToDTO);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid language: " + language);
        }
    }

    @Override
    public Page<MovieDTO> getMoviesByStatus(String status, int pageNo, int pageSize) {
        log.info("Fetching movies by status: {}, page: {}, size: {}", status, pageNo, pageSize);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("releaseDate").descending());

        try {
            MovieStatus statusEnum = MovieStatus.valueOf(status.toUpperCase());
            Page<Movie> moviePage = movieRepository.findByStatusAndIsActiveTrue(statusEnum, pageable);
            return moviePage.map(movieMapper::convertEntityToDTO);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }

    @Override
    @Transactional
    public MovieDTO updateMovie(Long id, MovieRequestDTO requestDTO) {
        log.info("Updating movie with ID: {}", id);

        Movie existingMovie = movieRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));

        if (!existingMovie.getTitle().equals(requestDTO.getTitle()) ||
                existingMovie.getGenre() != requestDTO.getGenre() ||
                existingMovie.getLanguage() != requestDTO.getLanguage()) {

            if (movieRepository.existsByTitleAndGenreAndLanguageAndIsActiveTrue(
                    requestDTO.getTitle(),
                    requestDTO.getGenre(),
                    requestDTO.getLanguage())) {
                throw new RuntimeException("Another movie already exists with title: " + requestDTO.getTitle() +
                        ", genre: " + requestDTO.getGenre() +
                        ", language: " + requestDTO.getLanguage());
            }
        }

        existingMovie = movieMapper.convertRequestDTOToEntity(requestDTO, existingMovie);
        Movie updatedMovie = movieRepository.save(existingMovie);
        log.info("Movie updated successfully with ID: {}", updatedMovie.getId());

        return movieMapper.convertEntityToDTO(updatedMovie);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        log.info("Deleting movie with ID: {}", id);

        Movie movie = movieRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + id));

        movie.setIsActive(false);
        movieRepository.save(movie);
        log.info("Movie deleted successfully with ID: {}", id);
    }
}