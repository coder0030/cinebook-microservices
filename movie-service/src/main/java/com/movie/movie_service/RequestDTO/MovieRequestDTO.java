package com.movie.movie_service.RequestDTO;

import com.movie.movie_service.Helper.Genre;
import com.movie.movie_service.Helper.Language;
import com.movie.movie_service.Helper.MovieStatus;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieRequestDTO {

    @NotBlank(message = "Movie title is required")
    private String title;

    private String description;

    @NotNull(message = "Genre is required")
    private Genre genre;

    @NotNull(message = "Language is required")
    private Language language;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 500, message = "Duration cannot exceed 500 minutes")
    private Integer durationMinutes;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10")
    private Double rating;

    @PastOrPresent(message = "Release date cannot be in the future")
    private LocalDate releaseDate;

    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL format")
    private String posterUrl;

    @NotNull(message = "Movie status is required")
    private MovieStatus status;
}