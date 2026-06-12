package com.movie.movie_service.Entity;
import com.movie.movie_service.Helper.ShowStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "shows",
        indexes = {
                @Index(name = "idx_show_movie_date", columnList = "movie_id, showDate"),
                @Index(name = "idx_show_theatre_date", columnList = "theatre_id, showDate"),
                @Index(name = "idx_show_screen_date", columnList = "screen_id, showDate"),
                @Index(name = "idx_show_movie_theatre_date", columnList = "movie_id, theatre_id, showDate")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_show_screen_datetime",
                        columnNames = {"screen_id", "showDate", "showTime", "endTime"}
                )
        }
)
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate showDate;

    @Column(nullable = false)
    private LocalTime showTime;

    @Column(nullable = false)
    private LocalTime endTime;

    private Integer availableSeats;

    @Column(nullable = false)
    private Double ticketPrice;

    @Enumerated(EnumType.STRING)
    private ShowStatus status;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    private List<ShowSeat> showSeatsList = new ArrayList<>();

    public void addShowSeat(ShowSeat showSeat) {
        showSeatsList.add(showSeat);
        showSeat.setShow(this);
    }

    public void removeShowSeats(ShowSeat showSeat) {
        showSeatsList.remove(showSeat);
        showSeat.setShow(null);
    }
}