package com.movie.user_service.DTO;

import com.movie.user_service.Helper.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long id;
    private Long userId;
    private Long movieId;
    private String movieName;
    private String theaterName;
    private LocalDateTime showTime;
    private Integer numberOfSeats;
    private Double totalPrice;
    private Status status;
}
