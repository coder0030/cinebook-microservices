package com.movie.movie_service.Controller;

import com.movie.movie_service.DTO.ShowSeatDTO;
import com.movie.movie_service.Service.ShowSeatService;
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

import java.util.Map;

@RestController
@RequestMapping("/movies/showseats")
@RequiredArgsConstructor
@Tag(name = "ShowSeat operations", description = "APIs for managing show seats")
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    @Operation(summary = "Get ShowSeat by ID", description = "Retrieve a specific show seat by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ShowSeat found successfully"),
            @ApiResponse(responseCode = "404", description = "ShowSeat not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ShowSeatDTO> getShowSeatById(@PathVariable Long id) {
        return ResponseEntity.ok(showSeatService.getShowSeatById(id));
    }

    @Operation(summary = "Get all ShowSeats by Show ID", description = "Retrieve all show seats for a specific show with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ShowSeats retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Show not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/show/{showId}")
    public ResponseEntity<Page<ShowSeatDTO>> getShowSeatsByShow(
            @PathVariable Long showId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(showSeatService.getShowSeatsByShow(showId, pageNo, pageSize));
    }

    @Operation(summary = "Get available ShowSeats by Show ID", description = "Retrieve only available show seats for a specific show with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available show seats retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Show not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/show/{showId}/available")
    public ResponseEntity<Page<ShowSeatDTO>> getAvailableShowSeatsByShow(
            @PathVariable Long showId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(showSeatService.getAvailableShowSeatsByShow(showId, pageNo, pageSize));
    }

    @Operation(summary = "Update ShowSeat price", description = "Update the price of a specific show seat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid price value"),
            @ApiResponse(responseCode = "404", description = "ShowSeat not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PutMapping("/{id}/price")
    public ResponseEntity<ShowSeatDTO> updateShowSeatPrice(
            @PathVariable Long id,
            @Valid @RequestBody Map<String, Double> request) {
        Double price = request.get("price");
        return ResponseEntity.ok(showSeatService.updateShowSeatPrice(id, price));
    }

    @Operation(summary = "Delete ShowSeat", description = "Remove a show seat from a show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "ShowSeat deleted successfully"),
            @ApiResponse(responseCode = "404", description = "ShowSeat not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowSeat(@PathVariable Long id) {
        showSeatService.deleteShowSeat(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Check Show and Seat Booking Status",
            description = "Check if a specific seat is booked for a particular show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking status retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Show or Seat not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/{seatId}/shows/{showId}/booked")  // CHANGED: Added /booked
    public ResponseEntity<Boolean> checkShowsAndSeatBooked(@PathVariable("seatId") Long seatId,
                                                           @PathVariable("showId") Long showId) {
        boolean isBooked = showSeatService.checkShowsAndSeatBooked(seatId, showId);
        return ResponseEntity.ok(isBooked);
    }

    @Operation(summary = "Get Seat Price",
            description = "Retrieve the price of a specific seat for a particular show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seat price retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Show or Seat not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/{seatId}/shows/{showId}/price")  // CHANGED: Added /price
    public ResponseEntity<Double> getSeatPrice(@PathVariable("seatId") Long seatId,
                                               @PathVariable("showId") Long showId) {
        Double price = showSeatService.getSeatPrice(seatId, showId);
        return ResponseEntity.ok(price);
    }
}