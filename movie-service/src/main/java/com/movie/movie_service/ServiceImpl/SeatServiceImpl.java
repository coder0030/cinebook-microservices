package com.movie.movie_service.ServiceImpl;

import com.movie.movie_service.DTO.SeatDTO;
import com.movie.movie_service.Entity.Seat;
import com.movie.movie_service.Entity.Screen;
import com.movie.movie_service.Entity.Show;
import com.movie.movie_service.Entity.ShowSeat;
import com.movie.movie_service.ExceptionHandler.ConflictException;
import com.movie.movie_service.ExceptionHandler.ResourceNotFoundException;
import com.movie.movie_service.Mapper.SeatMapper;
import com.movie.movie_service.Repository.SeatRepository;
import com.movie.movie_service.Repository.ScreenRepository;
import com.movie.movie_service.Repository.ShowRepository;
import com.movie.movie_service.Repository.ShowSeatRepository;
import com.movie.movie_service.RequestDTO.SeatRequestDTO;
import com.movie.movie_service.Service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final ScreenRepository screenRepository;
    private final SeatMapper seatMapper;

    @Override
    @Transactional
    public SeatDTO createSeat(SeatRequestDTO requestDTO) {

        Screen screen = screenRepository.findByIdAndIsActiveTrue(requestDTO.getScreenId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Screen not found with id: " + requestDTO.getScreenId()));

        validateUniqueSeat(screen.getId(), requestDTO.getRowLabel(),
                requestDTO.getSeatNumber(), null);

        Seat seat = new Seat();
        seatMapper.convertRequestDTOToEntity(requestDTO, seat);
        seat.setScreen(screen);
        seat.setIsBooked(false);

        if (requestDTO.getShowId() != null) {
            Show show = showRepository.findByIdAndIsActiveTrue(requestDTO.getShowId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Show not found with id: " + requestDTO.getShowId()));

            if (!show.getScreen().getId().equals(screen.getId())) {
                throw new ConflictException(
                        "Show does not belong to the given screen");
            }

            ShowSeat showSeat = new ShowSeat();
            showSeat.setShow(show);
            showSeat.setSeat(seat);
            showSeat.setPrice(requestDTO.getPrice());
            showSeat.setIsBooked(false);
            seat.addShowSeat(showSeat);
            showSeatRepository.save(showSeat);
        }

        Seat savedSeat = seatRepository.save(seat);
        return seatMapper.convertEntityToDTO(savedSeat);
    }

    @Override
    public SeatDTO getSeatById(Long id) {
        Seat seat = seatRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seat not found with id: " + id));
        return seatMapper.convertEntityToDTO(seat);
    }

    @Override
    @Transactional
    public SeatDTO updateSeat(Long id, SeatRequestDTO requestDTO) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seat not found with id: " + id));

        if (requestDTO.getRowLabel() != null && requestDTO.getSeatNumber() != null) {
            if (!requestDTO.getRowLabel().equals(seat.getRowLabel()) ||
                    !requestDTO.getSeatNumber().equals(seat.getSeatNumber())) {
                validateUniqueSeat(seat.getScreen().getId(),
                        requestDTO.getRowLabel(), requestDTO.getSeatNumber(), id);
            }
        }

        if (requestDTO.getShowId() != null) {
            Long currentShowId = seat.getShowSeatsList().stream()
                    .findFirst()
                    .map(ss -> ss.getShow().getId())
                    .orElse(null);

            if (!requestDTO.getShowId().equals(currentShowId)) {
                Show newShow = showRepository.findById(requestDTO.getShowId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Show not found with id: " + requestDTO.getShowId()));

                if (!newShow.getScreen().getId().equals(seat.getScreen().getId())) {
                    throw new ConflictException(
                            "New show does not belong to the same screen as this seat");
                }

                ShowSeat showSeat = seat.getShowSeatsList().stream()
                        .findFirst()
                        .orElse(null);

                if (showSeat != null) {
                    showSeat.setShow(newShow);
                    if (requestDTO.getPrice() != null) {
                        showSeat.setPrice(requestDTO.getPrice());
                    }
                }
            }
        }

        seatMapper.convertRequestDTOToEntity(requestDTO, seat);

        Seat updatedSeat = seatRepository.save(seat);
        return seatMapper.convertEntityToDTO(updatedSeat);
    }

    @Override
    @Transactional
    public SeatDTO bookSeat(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seat not found with id: " + id));

        if (Boolean.TRUE.equals(seat.getIsBooked())) {
            throw new ConflictException("Seat is already booked");
        }

        seat.setIsBooked(true);

        for (ShowSeat showSeat : seat.getShowSeatsList()) {
            if (Boolean.FALSE.equals(showSeat.getIsBooked())) {

                Show show = showSeat.getShow();
                if (show.getShowDate().isBefore(LocalDate.now())) {
                    throw new ConflictException(
                            "Cannot book seat for a past show");
                }

                showSeat.setIsBooked(true);

                if (show.getAvailableSeats() != null && show.getAvailableSeats() > 0) {
                    show.setAvailableSeats(show.getAvailableSeats() - 1);
                    showRepository.save(show);
                }
                break;
            }
        }

        Seat updatedSeat = seatRepository.save(seat);
        return seatMapper.convertEntityToDTO(updatedSeat);
    }

    @Override
    @Transactional
    public SeatDTO cancelSeatBooking(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seat not found with id: " + id));

        if (Boolean.FALSE.equals(seat.getIsBooked())) {
            throw new ConflictException("Seat is not currently booked");
        }

        seat.setIsBooked(false);

        for (ShowSeat showSeat : seat.getShowSeatsList()) {
            if (Boolean.TRUE.equals(showSeat.getIsBooked())) {
                showSeat.setIsBooked(false);

                Show show = showSeat.getShow();
                if (show.getAvailableSeats() != null) {
                    show.setAvailableSeats(show.getAvailableSeats() + 1);
                    showRepository.save(show);
                }
                break;
            }
        }

        Seat updatedSeat = seatRepository.save(seat);
        return seatMapper.convertEntityToDTO(updatedSeat);
    }

    @Override
    @Transactional
    public void deleteSeat(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Seat not found with id: " + id));

        seat.setIsActive(false);
        seatRepository.save(seat);
    }

    @Override
    @Transactional
    public Page<SeatDTO> createMultipleSeats(List<SeatRequestDTO> requestDTOs) {

        if (requestDTOs == null || requestDTOs.isEmpty()) {
            throw new IllegalArgumentException("Seat list cannot be empty");
        }

        Long screenId = requestDTOs.get(0).getScreenId();

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Screen not found with id: " + screenId));

        boolean allSameScreen = requestDTOs.stream()
                .allMatch(dto -> screenId.equals(dto.getScreenId()));

        if (!allSameScreen) {
            throw new IllegalArgumentException(
                    "All seats must belong to the same screen");
        }

        Long showId = requestDTOs.get(0).getShowId();
        Show show = null;

        if (showId != null) {

            boolean allSameShow = requestDTOs.stream()
                    .allMatch(dto -> showId.equals(dto.getShowId()));

            if (!allSameShow) {
                throw new IllegalArgumentException(
                        "All seats must belong to the same show");
            }

            show = showRepository.findById(showId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Show not found with id: " + showId));

            if (!show.getScreen().getId().equals(screen.getId())) {
                throw new ConflictException(
                        "Show does not belong to the given screen");
            }
        }

        final Show finalShow = show;

        List<Seat> seats = requestDTOs.stream()
                .map(requestDTO -> {

                    validateUniqueSeat(screen.getId(), requestDTO.getRowLabel(),
                            requestDTO.getSeatNumber(), null);

                    Seat seat = new Seat();

                    seatMapper.convertRequestDTOToEntity(
                            requestDTO,
                            seat);

                    seat.setScreen(screen);
                    seat.setIsBooked(false);

                    if (finalShow != null) {

                        ShowSeat showSeat = new ShowSeat();
                        showSeat.setShow(finalShow);
                        showSeat.setSeat(seat);
                        showSeat.setPrice(requestDTO.getPrice());
                        showSeat.setIsBooked(false);

                        seat.addShowSeat(showSeat);
                    }

                    return seat;
                })
                .toList();

        List<Seat> savedSeats = seatRepository.saveAll(seats);

        List<SeatDTO> seatDTOs = savedSeats.stream()
                .map(seatMapper::convertEntityToDTO)
                .toList();

        return new PageImpl<>(seatDTOs, PageRequest.of(0, seatDTOs.size()),
                seatDTOs.size());
    }

    @Override
    public Page<SeatDTO> getSeatsByShow(Long showId, int pageNo, int pageSize) {
        showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Show not found with id: " + showId));

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Seat> seatsPage = seatRepository
                .findByShowSeatsList_Show_Id(showId, pageable);

        return seatsPage.map(seatMapper::convertEntityToDTO);
    }

    @Override
    public List<SeatDTO> getAvailableSeatsByShow(Long showId) {
        showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Show not found with id: " + showId));

        List<Seat> seatsList = seatRepository
                .findByShowSeatsList_Show_IdAndShowSeatsList_IsBookedFalse(showId);

        return seatsList.stream()
                .map(seatMapper::convertEntityToDTO)
                .collect(Collectors.toList());
    }

    private void validateUniqueSeat(Long screenId, String rowLabel,
                                    String seatNumber, Long excludeSeatId) {
        Seat existingSeat = seatRepository
                .findByScreen_IdAndRowLabelAndSeatNumber(screenId, rowLabel, seatNumber);

        if (existingSeat != null &&
                (excludeSeatId == null || !existingSeat.getId().equals(excludeSeatId))) {
            throw new ConflictException(
                    String.format("Seat %s%s already exists in this screen",
                            rowLabel, seatNumber));
        }
    }
 }