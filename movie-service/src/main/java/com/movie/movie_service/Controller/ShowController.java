package com.movie.movie_service.Controller;

import com.movie.movie_service.DTO.ShowDTO;
import com.movie.movie_service.RequestDTO.ShowRequestDTO;
import com.movie.movie_service.Service.ShowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
public class ShowController {

    private final ShowService showService;

    @PostMapping("/create")
    public ResponseEntity<ShowDTO> createShow(@Valid @RequestBody ShowRequestDTO requestDTO) {
        return new ResponseEntity<>(showService.createShow(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowDTO> getShowById(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowDTO>> getShowsByMovie(@PathVariable Long movieId,
                                                         @RequestParam(defaultValue = "0") int pageNo,
                                                         @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<ShowDTO>> getShowsByTheatre(@PathVariable Long theatreId,
                                                           @RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(showService.getShowsByTheatre(theatreId, pageNo, pageSize));
    }

    @GetMapping("/screen/{screenId}/date")
    public ResponseEntity<List<ShowDTO>> getShowsByScreenAndDate(
            @PathVariable Long screenId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(showService.getShowsByScreenAndDate(screenId, date, pageNo, pageSize));
    }

    @GetMapping("/date")
    public ResponseEntity<List<ShowDTO>> getShowsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(showService.getShowsByDate(date, pageNo, pageSize));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowDTO> updateShow(@PathVariable Long id, @Valid @RequestBody ShowRequestDTO requestDTO) {
        return ResponseEntity.ok(showService.updateShow(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }
}