package com.movie.movie_service.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "theatres",
        indexes = {
                @Index(name = "idx_theatre_name", columnList = "name"),
                @Index(name = "idx_theatre_city", columnList = "city"),
                @Index(name = "idx_theatre_state", columnList = "state"),
                @Index(name = "idx_theatre_city_state", columnList = "city, state"),
                @Index(name = "idx_theatre_name_city", columnList = "name, city")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_name_city_state",
                        columnNames = {"name", "city", "state"})
        }
)
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;

    private String city;

    private String state;

    private Integer totalScreens;

    private boolean isActive;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Screen> screensList = new HashSet<>();

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    private Set<Show> showsList = new HashSet<>();

    public void addScreen(Screen screen) {
        screensList.add(screen);
        screen.setTheatre(this);
    }

    public void removeScreen(Screen screen) {
        screensList.remove(screen);
        screen.setTheatre(null);
    }

    public void addShow(Show show) {
        showsList.add(show);
        show.setTheatre(this);
    }

    public void removeShow(Show show) {
        showsList.remove(show);
        show.setTheatre(null);
    }

    @PrePersist
    protected void onCreate() {
        isActive = true;
    }
}