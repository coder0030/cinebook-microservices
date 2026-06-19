package com.movie.booking_service.Entity;

import com.movie.booking_service.Helper.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "bookings",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_show",
                        columnNames = {"user_id", "show_id"})
        },
        indexes = {
                @Index(name = "idx_userId", columnList = "userId"),
                @Index(name = "idx_showId", columnList = "showId"),
                @Index(name = "idx_bookingId", columnList = "bookingId")
        }
)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private Long id;

    @Column(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "showId", nullable = false)
    private Long showId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookingSeat> bookingSeats = new ArrayList<>();

    public void addBookingSeat(BookingSeat bookingSeat) {
        if (bookingSeats == null) {
            bookingSeats = new ArrayList<>();
        }
        bookingSeats.add(bookingSeat);
        bookingSeat.setBooking(this);
    }

    public void removeBookingSeat(BookingSeat bookingSeat) {
        if (bookingSeats != null) {
            bookingSeats.remove(bookingSeat);
            bookingSeat.setBooking(null);
        }
    }
}