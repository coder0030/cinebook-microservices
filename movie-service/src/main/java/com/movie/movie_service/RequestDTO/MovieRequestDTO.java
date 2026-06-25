package com.movie.movie_service.RequestDTO;

import com.movie.movie_service.Helper.Genre;
import com.movie.movie_service.Helper.Language;
import com.movie.movie_service.Helper.MovieStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Request object for creating/updating a movie")
public class MovieRequestDTO {

    @NotBlank(message = "Movie title is required")
    @Schema(description = "Movie Title", example = "Inception", required = true, minLength = 1, maxLength = 200)
    private String title;

    @Schema(description = "Detailed description of the movie plot", example = "A thief who steals corporate secrets through dream-sharing technology", maxLength = 1000)
    private String description;

    @NotNull(message = "Genre is required")
    @Schema(description = "Movie genre classification", required = true,
            example = "ACTION", allowableValues = {"ACTION", "COMEDY", "DRAMA", "HORROR", "SCI_FI", "ROMANCE", "THRILLER"})
    private Genre genre;

    @NotNull(message = "Language is required")
    @Schema(description = "Movie language", required = true,
            example = "ENGLISH", allowableValues = {"ENGLISH", "HINDI", "KANADA", "BHOJPURI", "TELGU", "TAMIL"})
    private Language language;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 500, message = "Duration cannot exceed 500 minutes")
    @Schema(description = "Movie duration in minutes", example = "148", required = true, minimum = "1", maximum = "500")
    private Integer durationMinutes;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0")
    @DecimalMax(value = "10.0", message = "Rating cannot exceed 10")
    @Schema(description = "IMDB or user rating (0-10)", example = "8.8", minimum = "0", maximum = "10", defaultValue = "0.0")
    private Double rating;

    @PastOrPresent(message = "Release date cannot be in the future")
    @Schema(description = "Movie release date (cannot be future date)", example = "2010-07-16", required = true)
    private LocalDate releaseDate;

    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid URL format")
    @Schema(description = "URL for movie poster image", example = "https://example.com/poster.jpg", pattern = "^(http|https)://.*$")
    private String posterUrl;

    @NotNull(message = "Movie status is required")
    @Schema(description = "Current status of the movie", required = true,
            example = "NOW_PLAYING",
            allowableValues = {"UPCOMING", "NOW_PLAYING", "RELEASED", "BLOCKBUSTER", "HIT", "FLOP", "RUNNING", "EXPIRED", "ENDED"})
    private MovieStatus status;

    @Schema(description = "Movie director name", example = "Christopher Nolan", maxLength = 100)
    private String director;
}