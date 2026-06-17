package com.movie.movie_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "show-seat",
        indexes = {
                @Index(name = "idx_id", columnList = "id"),
                @Index(name = "idx_show", columnList = "show_id")
        }
)
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean isActive = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(nullable = false)
    private Boolean isBooked = false;

    private LocalDateTime bookingTime;
    private Double price;

    public void setIsBooked(boolean booked) {
        isBooked = booked;
    }

    @PrePersist
    protected void onCreate() {
        isActive = true;
    }

}

