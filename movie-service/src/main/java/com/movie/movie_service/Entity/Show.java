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
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_show_screen_datetime",
                        columnNames = {"screen_id", "showDate", "showTime"}
                )
        }
)
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @Column(nullable = false)
    private LocalDate showDate;

    @Column(nullable = false)
    private LocalTime showTime;

    private Integer availableSeats;

    @Column(nullable = false)
    private Double ticketPrice;

    @Enumerated(EnumType.STRING)
    private ShowStatus status;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats = new ArrayList<>();

    public void addSeat(Seat seat) {
        seats.add(seat);
        seat.setShow(this);
    }

    public void removeSeat(Seat seat) {
        seats.remove(seat);
        seat.setShow(null);
    }


}