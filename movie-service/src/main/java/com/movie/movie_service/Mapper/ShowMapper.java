package com.movie.movie_service.Mapper;

import com.movie.movie_service.DTO.ShowDTO;
import com.movie.movie_service.Entity.Show;
import com.movie.movie_service.RequestDTO.ShowRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ShowMapper {

    public Show convertRequestDTOToEntity(ShowRequestDTO requestDTO, Show show) {
        show.setShowDate(requestDTO.getShowDate());
        show.setShowTime(requestDTO.getShowTime());
        show.setEndTime(requestDTO.getEndTime());
        show.setTicketPrice(requestDTO.getTicketPrice());
        show.setStatus(requestDTO.getStatus());
        return show;
    }

    public ShowDTO convertEntityToDTO(Show show) {
        return ShowDTO.builder()
                .id(show.getId())
                .showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .endTime(show.getEndTime())
                .availableSeats(show.getAvailableSeats())
                .ticketPrice(show.getTicketPrice())
                .status(show.getStatus())
                .movieId(show.getMovie() != null ? show.getMovie().getId() : null)
                .screenId(show.getScreen() != null ? show.getScreen().getId() : null)
                .screenName(show.getScreen() != null ? show.getScreen().getScreenName() : null)
                .theatreId(show.getTheatre() != null ? show.getTheatre().getId() : null)
                .theatreName(show.getTheatre() != null ? show.getTheatre().getName() : null)
                .build();
    }
}