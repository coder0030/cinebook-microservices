package com.movie.movie_service.DTO;

import com.movie.movie_service.Helper.ShowStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {
    private Long id;
    private Long movieId;
    private String movieTitle;
    private Long screenId;
    private String screenName;
    private Long theatreId;
    private String theatreName;
    private LocalDate showDate;
    private LocalTime showTime;
    private Integer availableSeats;
    private Double ticketPrice;
    private ShowStatus status;
    private List<SeatDTO> seats;
}