package com.movie.movie_service.Entity;
import com.movie.movie_service.Helper.ScreenType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "screens",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_screen_theatre",
                        columnNames = {"screenName", "theatre_id"}
                )
        }
)
public class Screen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String screenName;

    private Integer totalSeats;

    @Enumerated(EnumType.STRING)
    private ScreenType screenType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Show> shows = new HashSet<>();

    public void addShow(Show show) {
        shows.add(show);
        show.setScreen(this);
    }

    public void removeShow(Show show) {
        shows.remove(show);
        show.setScreen(null);
    }
}