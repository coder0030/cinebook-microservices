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
        indexes = {
                @Index(name = "idx_screen_theatre_id", columnList = "theatre_id"),
                @Index(name = "idx_screen_type", columnList = "screenType"),
                @Index(name = "idx_screen_theatre_type", columnList = "theatre_id, screenType"),
                @Index(name = "idx_screen_name", columnList = "screenName"),
                @Index(name = "idx_screen_total_seats", columnList = "totalSeats")
        },
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
    private Set<Show> showsList = new HashSet<>();

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Seat> seatsList = new HashSet<>();

    public void addShow(Show show) {
        showsList.add(show);
        show.setScreen(this);
    }

    public void removeShow(Show show) {
        showsList.remove(show);
        show.setScreen(null);
    }

    public void addSeats(Seat seat) {
        seatsList.add(seat);
        seat.setScreen(this);
    }

    public void removeSeats(Seat seat) {
        seatsList.remove(seat);
        seat.setScreen(null);
    }
}