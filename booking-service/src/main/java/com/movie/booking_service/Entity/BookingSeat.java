package com.movie.booking_service.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "booking_seats",
        indexes = {
                @Index(name = "idx_booking_id", columnList = "booking_id"),
                @Index(name = "idx_show_seat_id", columnList = "show_seat_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_booking_show_seat",
                        columnNames = {"booking_id", "show_seat_id"})
        }
)
public class BookingSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingSeatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name = "showSeatId", nullable = false)
    private Long showSeatId;

    @Column(nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private Double price;
}