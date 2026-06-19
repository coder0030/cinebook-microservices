package com.movie.booking_service.ServiceImpl;

import com.movie.booking_service.DTO.BookingResponseDTO;
import com.movie.booking_service.Entity.Booking;
import com.movie.booking_service.Entity.BookingSeat;
import com.movie.booking_service.ExceptionHandler.DuplicateBookingException;
import com.movie.booking_service.ExceptionHandler.ResourceNotFoundException;
import com.movie.booking_service.ExceptionHandler.SeatAlreadyBookedException;
import com.movie.booking_service.ExceptionHandler.UserNotFoundException;
import com.movie.booking_service.Helper.BookingStatus;
import com.movie.booking_service.Mapper.BookingMapper;
import com.movie.booking_service.Mapper.BookingSeatMapper;
import com.movie.booking_service.Repository.BookingRepository;
import com.movie.booking_service.RequestDTO.BookingRequestDTO;
import com.movie.booking_service.Service.BookingService;
import com.movie.booking_service.Service.MovieServiceClient;
import com.movie.booking_service.Service.UserClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final UserClient userClient;
    private final MovieServiceClient movieClient;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final BookingSeatMapper bookingSeatMapper;

    private void checkUserAndShowExistsOrNot(Long userId, Long showId) {
        if (userId != null && Boolean.FALSE.equals(userClient.userExists(userId))) {
            throw new UserNotFoundException("UserId: " + userId + ", not exists");
        }

        if (showId != null && !movieClient.checkShowsExists(showId)) {
            throw new ResourceNotFoundException("showId: " + showId + ", not exists");
        }
    }

    private void checkUserAlreadyBooked(Long userId, Long showId) {
        boolean hasActiveBooking = bookingRepository.existsByUserIdAndShowIdAndStatusIn(
                userId, showId, List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
        );

        if (hasActiveBooking) {
            throw new DuplicateBookingException("You have already one active booking for this show.");
        }
    }

    @Override
    @CircuitBreaker(name = "bookingService", fallbackMethod = "createBookingFallback")
    @Retry(name = "bookingService")
    public BookingResponseDTO createBooking(BookingRequestDTO requestDTO) {

        checkUserAndShowExistsOrNot(requestDTO.getUserId(), requestDTO.getShowId());
        checkUserAlreadyBooked(requestDTO.getUserId(), requestDTO.getShowId());

        requestDTO.getShowSeatIds().forEach(seatId -> {
            if (movieClient.checkShowsAndSeatBooked(seatId, requestDTO.getShowId())) {
                throw new SeatAlreadyBookedException("SeatId: " + seatId + " for showId: " +
                        requestDTO.getShowId() + ", already booked");
            }
        });

        Double totalPrice = requestDTO.getShowSeatIds().stream()
                .mapToDouble(seatId -> movieClient.getSeatPrice(seatId, requestDTO.getShowId()))
                .sum();

        Booking booking = bookingMapper.convertRequestDTOToEntity(requestDTO, new Booking());
        booking.setTotalAmount(totalPrice);

        List<BookingSeat> bookingSeats = new ArrayList<>();
        requestDTO.getShowSeatIds().forEach(seatId -> {
            Double price = movieClient.getSeatPrice(seatId, requestDTO.getShowId());
            BookingSeat bookingSeat = BookingSeat.builder()
                    .showSeatId(seatId)
                    .seatNumber("Seat-" + seatId)
                    .price(price)
                    .booking(booking)
                    .build();
            bookingSeats.add(bookingSeat);
        });

        booking.setBookingSeats(bookingSeats);

        Booking savedBooking = bookingRepository.save(booking);
        log.info("Booking created successfully with ID: {}", savedBooking.getId());

        return bookingMapper.convertEntityToDTO(savedBooking);
    }

    public BookingResponseDTO createBookingFallback(BookingRequestDTO requestDTO, Throwable ex) {
        log.warn("Circuit breaker fallback triggered for createBooking. Reason: {}", ex.getMessage());

        return BookingResponseDTO.builder()
                .bookingId(null)
                .userId(requestDTO.getUserId())
                .showId(requestDTO.getShowId())
                .totalAmount(0.0)
                .status(BookingStatus.FAILED)
                .errorMessage("Booking service is currently unavailable. Please try again later.")
                .bookingSeats(null)
                .build();
    }


    public BookingResponseDTO getBookingById(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
        return bookingMapper.convertEntityToDTO(booking);
    }

    @Override
    @CircuitBreaker(name = "userService", fallbackMethod = "getBookingsByUserIdFallback")
    @Retry(name = "bookingService")
    public Page<BookingResponseDTO> getBookingsByUserId(Long userId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if (!userClient.userExists(userId)) {
            throw new UserNotFoundException("User-id: " + userId + " not exists.");
        }

        Page<Booking> bookingPage = bookingRepository.findByUserId(userId, pageable);
        if (bookingPage.isEmpty()) {
            return Page.empty(pageable);
        }

        return bookingPage.map(bookingMapper::convertEntityToDTO);
    }

    public Page<BookingResponseDTO> getBookingsByUserIdFallback(Long userId, int pageNo, int pageSize, Exception ex) {
        log.error("Fallback executed for getBookingsByUserId with userId: {}, error: {}", userId, ex.getMessage());

        BookingResponseDTO errorResponse = BookingResponseDTO.builder()
                .userId(userId)
                .errorMessage("Service is currently unavailable. Please try again later.")
                .status(BookingStatus.FAILED)
                .build();

        List<BookingResponseDTO> errorList = Collections.singletonList(errorResponse);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return new PageImpl<>(errorList, pageable, errorList.size());
    }

    @Override
    @CircuitBreaker(name = "showService", fallbackMethod = "getBookingsByShowIdFallback")
    @Retry(name = "bookingService")
    public Page<BookingResponseDTO> getBookingsByShowId(Long showId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        if (!movieClient.checkShowsExists(showId)) {
            throw new UserNotFoundException("User-id: " + showId + " not exists.");
        }

        Page<Booking> bookingPage = bookingRepository.findByshowId(showId, pageable);
        if (bookingPage.isEmpty()) {
            return Page.empty(pageable);
        }

        return bookingPage.map(bookingMapper::convertEntityToDTO);
    }

    public Page<BookingResponseDTO> getBookingsByShowIdFallback(Long showId, int pageNo, int pageSize, Exception ex) {
        log.error("Fallback executed for getBookingsByShowId with showId: {}, error: {}", showId, ex.getMessage());

         BookingResponseDTO errorResponse = BookingResponseDTO.builder()
                 .showId(showId)
                 .errorMessage("Service is currently unavailable. Please try again later.")
                 .status(BookingStatus.FAILED)
                 .build();

        List<BookingResponseDTO> errorList = Collections.singletonList(errorResponse);

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return new PageImpl<>(errorList, pageable, errorList.size());
    }

    @Override
    public BookingResponseDTO updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        booking.setStatus(status);
        Booking updatedBooking = bookingRepository.save(booking);
        log.info("Booking status updated for ID: {} to {}", bookingId, status);

        return bookingMapper.convertEntityToDTO(updatedBooking);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        log.info("Booking cancelled with ID: {}", bookingId);
    }

    @Override
    public BookingResponseDTO confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new IllegalStateException("Only PENDING bookings can be confirmed");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        Booking confirmedBooking = bookingRepository.save(booking);
        log.info("Booking confirmed with ID: {}", bookingId);

        return bookingMapper.convertEntityToDTO(confirmedBooking);
    }

    @Override
    public Page<BookingResponseDTO> getActiveBookings(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<Booking> activeBookings = bookingRepository.findByStatusIn(List.of(BookingStatus.PENDING, BookingStatus.CONFIRMED)
                ,pageable);
        if (activeBookings.isEmpty()) {
            return Page.empty(pageable);
        }

        return activeBookings.map(bookingMapper::convertEntityToDTO);
    }
}
