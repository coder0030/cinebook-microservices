package com.movie.movie_service.ServiceImpl;

import com.movie.movie_service.DTO.ShowDTO;
import com.movie.movie_service.Entity.Movie;
import com.movie.movie_service.Entity.Screen;
import com.movie.movie_service.Entity.Show;
import com.movie.movie_service.Entity.Theatre;
import com.movie.movie_service.ExceptionHandler.ConflictException;
import com.movie.movie_service.ExceptionHandler.ResourceNotFoundException;
import com.movie.movie_service.Mapper.ShowMapper;
import com.movie.movie_service.Repository.MovieRepository;
import com.movie.movie_service.Repository.ScreenRepository;
import com.movie.movie_service.Repository.ShowRepository;
import com.movie.movie_service.Repository.TheatreRepository;
import com.movie.movie_service.RequestDTO.ShowRequestDTO;
import com.movie.movie_service.Service.ShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;
    private final ShowMapper showMapper;

    @Override
    public ShowDTO createShow(ShowRequestDTO requestDTO) {

        Movie movie = getMovie(requestDTO.getMovieId());
        Screen screen = getScreen(requestDTO.getScreenId());

        validateShowTiming(
                screen,
                requestDTO.getShowDate(),
                requestDTO.getShowTime(),
                requestDTO.getEndTime(),
                null
        );

        Show show = new Show();

        showMapper.convertRequestDTOToEntity(requestDTO, show);

        show.setMovie(movie);
        show.setScreen(screen);
        show.setTheatre(screen.getTheatre());

        if (requestDTO.getAvailableSeats() == null) {
            show.setAvailableSeats(screen.getTotalSeats());
        }

        return toDTO(showRepository.save(show));
    }

    @Override
    @Transactional(readOnly = true)
    public ShowDTO getShowById(Long id) {
        return toDTO(getShow(id));
    }

    @Override
    public ShowDTO updateShow(Long id, ShowRequestDTO requestDTO) {

        Show show = getShow(id);

        updateMovie(show, requestDTO);
        updateScreen(show, requestDTO);

        validateUpdatedTiming(show, requestDTO, id);

        showMapper.convertRequestDTOToEntity(requestDTO, show);

        if (requestDTO.getAvailableSeats() != null) {
            show.setAvailableSeats(requestDTO.getAvailableSeats());
        }

        return toDTO(showRepository.save(show));
    }

    @Override
    public void deleteShow(Long id) {
        showRepository.delete(getShow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowDTO> getShowsByMovie(Long movieId) {

        getMovie(movieId);

        return showRepository.findByMovie_IdAndIsActiveTrue(movieId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowDTO> getShowsByTheatre(Long theatreId, int pageNo, int pageSize) {

        getTheatre(theatreId);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return showRepository
                .findByTheatre_IdAndIsActiveTrue(theatreId, pageable)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowDTO> getShowsByScreenAndDate(Long screenId, LocalDate date, int pageNo,
                                                 int pageSize) {
        getScreen(screenId);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return showRepository
                .findByScreen_IdAndShowDateAndIsActiveTrue(screenId, date, pageable)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShowDTO> getShowsByDate(LocalDate date, int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return showRepository
                .findByShowDateAndIsActiveTrue(date, pageable)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private ShowDTO toDTO(Show show) {
        return showMapper.convertEntityToDTO(show);
    }

    private Show getShow(Long id) {
        return showRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Show not found with id: " + id
                        ));
    }

    private Movie getMovie(Long id) {
        return movieRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Movie not found with id: " + id
                        ));
    }

    private Screen getScreen(Long id) {
        return screenRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Screen not found with id: " + id
                        ));
    }

    private Theatre getTheatre(Long id) {
        return theatreRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Theatre not found with id: " + id
                        ));
    }

    private void updateMovie(Show show, ShowRequestDTO requestDTO) {

        if (requestDTO.getMovieId() == null) {
            return;
        }

        if (!requestDTO.getMovieId()
                .equals(show.getMovie().getId())) {

            show.setMovie(getMovie(requestDTO.getMovieId()));
        }
    }

    private void updateScreen(Show show, ShowRequestDTO requestDTO) {

        if (requestDTO.getScreenId() == null) {
            return;
        }

        if (!requestDTO.getScreenId()
                .equals(show.getScreen().getId())) {

            Screen screen = getScreen(requestDTO.getScreenId());

            show.setScreen(screen);
            show.setTheatre(screen.getTheatre());
        }
    }

    private void validateUpdatedTiming(Show show, ShowRequestDTO dto, Long showId) {

        LocalDate date = dto.getShowDate() != null ? dto.getShowDate() : show.getShowDate();

        LocalTime startTime = dto.getShowTime() != null ? dto.getShowTime() : show.getShowTime();

        LocalTime endTime = dto.getEndTime() != null ? dto.getEndTime() : show.getEndTime();

        validateShowTiming(show.getScreen(), date, startTime, endTime, showId);
    }

    private void validateShowTiming(Screen screen, LocalDate showDate, LocalTime startTime,
                                    LocalTime endTime, Long excludeShowId) {

        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException(
                    "End time must be after start time"
            );
        }

        List<Show> overlappingShows = showRepository.findOverlappingShows(
                        screen.getId(), showDate, startTime, endTime, excludeShowId);

        if (excludeShowId != null) {
            overlappingShows = overlappingShows.stream()
                    .filter(show ->
                            !show.getId().equals(excludeShowId))
                    .toList();
        }

        if (!overlappingShows.isEmpty()) {
            throw new ConflictException(
                    "Show timing overlaps with existing show"
            );
        }
    }
}