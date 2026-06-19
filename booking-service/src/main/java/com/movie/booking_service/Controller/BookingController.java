package com.movie.booking_service.Controller;

import com.movie.booking_service.DTO.BookingResponseDTO;
import com.movie.booking_service.Entity.Booking;
import com.movie.booking_service.Entity.BookingSeat;
import com.movie.booking_service.Helper.BookingStatus;
import com.movie.booking_service.RequestDTO.BookingRequestDTO;
import com.movie.booking_service.Service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(
        name = "Booking Service",
        description = "APIs for managing movie bookings - create, update, cancel, and retrieve bookings."
)
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Create a new booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Booking created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failed"),
            @ApiResponse(responseCode = "404", description = "Show or seats not found"),
            @ApiResponse(responseCode = "409", description = "Seats already booked"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO requestDTO) {
        BookingResponseDTO response = bookingService.createBooking(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Get booking details by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking found successfully"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable Long bookingId) {
        BookingResponseDTO response = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all bookings for a specific user with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<BookingResponseDTO>> getBookingsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        Page<BookingResponseDTO> responses = bookingService.getBookingsByUserId(userId, pageNo, pageSize);
        return ResponseEntity.ok(responses);
    }


    @Operation(summary = "Get all bookings for a specific show with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bookings retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Show not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/show/{showId}")
    public ResponseEntity<Page<BookingResponseDTO>> getBookingsByShowId(
            @PathVariable Long showId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        Page<BookingResponseDTO> responses = bookingService.getBookingsByShowId(showId, pageNo, pageSize);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Update booking status (PENDING, CONFIRMED, CANCELLED, COMPLETED)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status or booking cannot be updated"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingResponseDTO> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status) {
        BookingResponseDTO response = bookingService.updateBookingStatus(bookingId, status);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cancel a booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Booking cannot be cancelled (already cancelled or completed)"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Confirm a pending booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking confirmed successfully"),
            @ApiResponse(responseCode = "400", description = "Booking cannot be confirmed (already confirmed or cancelled)"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponseDTO> confirmBooking(@PathVariable Long bookingId) {
        BookingResponseDTO response = bookingService.confirmBooking(bookingId);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Get all active bookings (CONFIRMED & PENDING) with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active bookings retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/active")
    public ResponseEntity<Page<BookingResponseDTO>> getActiveBookings(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        Page<BookingResponseDTO> responses = bookingService.getActiveBookings(pageNo, pageSize);
        return ResponseEntity.ok(responses);
    }
}