package com.movie.movie_service.Entity;
import com.movie.movie_service.Helper.SeatType;
import jakarta.persistence.*;
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
        name = "seats",
        indexes = {
                @Index(name = "idx_seat_screen_id", columnList = "screen_id"),
                @Index(name = "idx_seat_screen_row", columnList = "screen_id, rowLabel"),
                @Index(name = "idx_seat_type", columnList = "seatType")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_seat_row",
                        columnNames = {"seatNumber", "rowLabel", "screen_id"})
}
)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    private String rowLabel;

    @Column(nullable = false)
    private Boolean isActive = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatType seatType;

    @Column(nullable = false)
    private Boolean isBooked = false;

    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL)
    private List<ShowSeat> showSeatsList = new ArrayList<>();

    public void addShowSeat(ShowSeat showSeat) {
        showSeatsList.add(showSeat);
        showSeat.setSeat(this);
    }

    public void removeShowSeats(ShowSeat showSeat) {
        showSeatsList.remove(showSeat);
        showSeat.setSeat(null);
    }

    public String getSeatLocation() {
        return rowLabel + seatNumber;
    }

    @PrePersist
    protected void onCreate() {
        isActive = true;
    }
}