package com.movie.movie_service.Repository;

import com.movie.movie_service.Entity.Movie;
import com.movie.movie_service.Helper.Genre;
import com.movie.movie_service.Helper.Language;
import com.movie.movie_service.Helper.MovieStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitleAndGenreAndLanguageAndIsActiveTrue(@NotBlank(message = "Movie title is required") String title, @NotNull(message = "Genre is required") Genre genre, @NotNull(message = "Language is required") Language language);

    Page<Movie> findByGenreAndIsActiveTrue(Genre genreEnum, Pageable pageable);

    Page<Movie> findByLanguageAndIsActiveTrue(Language languageEnum, Pageable pageable);

    Page<Movie> findByStatusAndIsActiveTrue(MovieStatus statusEnum, Pageable pageable);

    Optional<Movie> findByIdAndIsActiveTrue(Long id);

    Page<Movie> findByIsActiveTrue(Pageable pageable);
}
