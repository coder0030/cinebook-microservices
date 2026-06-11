package com.movie.movie_service.Controller;

import com.movie.movie_service.DTO.SeatDTO;
import com.movie.movie_service.RequestDTO.SeatRequestDTO;
import com.movie.movie_service.Service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/create")
    public ResponseEntity<SeatDTO> createSeat(@Valid @RequestBody SeatRequestDTO requestDTO) {
        return new ResponseEntity<>(seatService.createSeat(requestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<SeatDTO>> createMultipleSeats(@Valid @RequestBody List<SeatRequestDTO> requestDTOs) {
        return new ResponseEntity<>(seatService.createMultipleSeats(requestDTOs), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatDTO> getSeatById(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.getSeatById(id));
    }

    @GetMapping("/show/{showId}")
    public ResponseEntity<List<SeatDTO>> getSeatsByShow(@PathVariable Long showId,
                                                        @RequestParam(defaultValue = "0") int pageNo,
                                                        @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(seatService.getSeatsByShow(showId, pageNo, pageSize));
    }

    @GetMapping("/show/{showId}/available")
    public ResponseEntity<List<SeatDTO>> getAvailableSeatsByShow(@PathVariable Long showId,
                                                                 @RequestParam(defaultValue = "0") int pageNo,
                                                                 @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(seatService.getAvailableSeatsByShow(showId, pageNo, pageSize));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatDTO> updateSeat(@PathVariable Long id, @Valid @RequestBody SeatRequestDTO requestDTO) {
        return ResponseEntity.ok(seatService.updateSeat(id, requestDTO));
    }

    @PatchMapping("/{id}/book")
    public ResponseEntity<SeatDTO> bookSeat(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.bookSeat(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<SeatDTO> cancelSeatBooking(@PathVariable Long id) {
        return ResponseEntity.ok(seatService.cancelSeatBooking(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }
}