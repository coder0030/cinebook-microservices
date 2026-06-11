package com.movie.movie_service.Entity;


import com.movie.movie_service.Helper.Genre;
import com.movie.movie_service.Helper.Language;
import com.movie.movie_service.Helper.MovieStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    private LocalDate releaseDate;

    private String posterUrl;

    @Enumerated(EnumType.STRING)
    private MovieStatus status;
}