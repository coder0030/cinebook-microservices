package com.movie.movie_service.Entity;
import com.movie.movie_service.Helper.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "seats", uniqueConstraints = {
                @UniqueConstraint(name = "uk_seat_row",
                        columnNames = {"seatNumber", "rowLabel", "show_id"})
}
)
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String seatNumber;

    private String rowLabel;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    private Boolean isBooked;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;
}