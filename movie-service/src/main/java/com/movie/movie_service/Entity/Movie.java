package com.movie.movie_service.Entity;


import com.movie.movie_service.Helper.Genre;
import com.movie.movie_service.Helper.Language;
import com.movie.movie_service.Helper.MovieStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "movies",
        indexes = {
                @Index(name = "idx_movie_id", columnList = "id"),
                @Index(name = "idx_movie_title", columnList = "title"),
                @Index(name = "idx_movie_genre", columnList = "genre")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_title_genre_language",
                        columnNames = {"title", "genre", "language"})
        }
)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private Language language;

    private Integer durationMinutes;

    private Double rating;

    private String director;

    private LocalDate releaseDate;

    private String posterUrl;

    @Enumerated(EnumType.STRING)
    private MovieStatus status;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Show> showsList = new HashSet<>();

    public void addShows(Show show) {
        showsList.add(show);
        show.setMovie(this);
    }

    public void removeShows(Show show) {
        showsList.remove(show);
        show.setMovie(null);
    }
}