package com.movie.movie_service.Controller;

import com.movie.movie_service.DTO.SeatDTO;
import com.movie.movie_service.RequestDTO.SeatRequestDTO;
import com.movie.movie_service.Service.SeatService;
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

import java.util.List;

@RestController
@RequestMapping("/movies/seats")
@RequiredArgsConstructor
@Tag(name = "Seat operations", description = "APIs for managing seats")
public class SeatController {

    private final SeatService seatService;

    @Operation(summary = "Create a new seat", description = "Add a new seat to a screen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Seat created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Screen not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping
    public ResponseEntity<SeatDTO> createSeat(@Valid @RequestBody SeatRequestDTO requestDTO) {
        return new ResponseEntity<>(seatService.createSeat(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Create multiple seats", description = "Add multiple seats to a screen in bulk")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Seats created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Screen not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping("/bulk")
    public ResponseEntity<Page<SeatDTO>> createMultipleSeats(@Valid @RequestBody List<SeatRequestDTO> requestDTOs) {
        return new ResponseEntity<>(seatService.createMultipleSeats(requestDTOs), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Seat by ID", description = "Retrieve a specific seat by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat found successfully"),
            @ApiResponse(responseCode = "404", description = "Seat not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SeatDTO> getSeatById(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.getSeatById(id));
    }

    @Operation(summary = "Get seats by Show ID", description = "Retrieve all seats for a specific show with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seats retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Show not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/show/{showId}")
    public ResponseEntity<Page<SeatDTO>> getSeatsByShow(
            @PathVariable Long showId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(seatService.getSeatsByShow(showId, pageNo, pageSize));
    }

    @Operation(summary = "Get available seats by Show ID", description = "Retrieve only available seats for a specific show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available seats retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Show not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/show/{showId}/available")
    public ResponseEntity<List<SeatDTO>> getAvailableSeatsByShow(@PathVariable Long showId) {
        return ResponseEntity.ok(seatService.getAvailableSeatsByShow(showId));
    }

    @Operation(summary = "Update Seat", description = "Update an existing seat by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Seat not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<SeatDTO> updateSeat(
            @PathVariable Long id,
            @Valid @RequestBody SeatRequestDTO requestDTO) {
        return ResponseEntity.ok(seatService.updateSeat(id, requestDTO));
    }

    @Operation(summary = "Book a seat", description = "Mark a specific seat as booked")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat booked successfully"),
            @ApiResponse(responseCode = "400", description = "Seat already booked"),
            @ApiResponse(responseCode = "404", description = "Seat not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PatchMapping("/{id}/book")
    public ResponseEntity<SeatDTO> bookSeat(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.bookSeat(id));
    }

    @Operation(summary = "Cancel seat booking", description = "Cancel the booking of a specific seat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking cancelled successfully"),
            @ApiResponse(responseCode = "400", description = "Seat is not booked"),
            @ApiResponse(responseCode = "404", description = "Seat not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<SeatDTO> cancelSeatBooking(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.cancelSeatBooking(id));
    }

    @Operation(summary = "Delete Seat", description = "Remove a seat from a screen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Seat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Seat not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }
}