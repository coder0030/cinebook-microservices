package com.movie.movie_service.Controller;

import com.movie.movie_service.DTO.ShowDTO;
import com.movie.movie_service.RequestDTO.ShowRequestDTO;
import com.movie.movie_service.Service.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/movies/shows")
@RequiredArgsConstructor
@Tag(name = "Show operations", description = "APIs for managing shows")
public class ShowController {

    private final ShowService showService;

    @Operation(summary = "Create a new show", description = "Add a new show to a screen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Show created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Movie or Screen not found"),
            @ApiResponse(responseCode = "409", description = "Show time conflict"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping
    public ResponseEntity<ShowDTO> createShow(@Valid @RequestBody ShowRequestDTO requestDTO) {
        return new ResponseEntity<>(showService.createShow(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Show by ID", description = "Retrieve a specific show by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show found successfully"),
            @ApiResponse(responseCode = "404", description = "Show not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ShowDTO> getShowById(@PathVariable Long id) {
        return ResponseEntity.ok(showService.getShowById(id));
    }

    @Operation(summary = "Get shows by Movie ID", description = "Retrieve all shows for a specific movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowDTO>> getShowsByMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(showService.getShowsByMovie(movieId));
    }

    @Operation(summary = "Get shows by Theatre ID", description = "Retrieve all shows for a specific theatre with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Theatre not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<List<ShowDTO>> getShowsByTheatre(
            @PathVariable Long theatreId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(showService.getShowsByTheatre(theatreId, pageNo, pageSize));
    }

    @Operation(summary = "Get shows by Screen and Date", description = "Retrieve all shows for a specific screen on a specific date with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Screen not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/screen/{screenId}/date")
    public ResponseEntity<List<ShowDTO>> getShowsByScreenAndDate(
            @PathVariable Long screenId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(showService.getShowsByScreenAndDate(screenId, date, pageNo, pageSize));
    }

    @Operation(summary = "Get shows by Date", description = "Retrieve all shows on a specific date with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Shows retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/date")
    public ResponseEntity<List<ShowDTO>> getShowsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(showService.getShowsByDate(date, pageNo, pageSize));
    }

    @Operation(summary = "Update Show", description = "Update an existing show by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Show not found"),
            @ApiResponse(responseCode = "409", description = "Show time conflict"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ShowDTO> updateShow(
            @PathVariable Long id,
            @Valid @RequestBody ShowRequestDTO requestDTO) {
        return ResponseEntity.ok(showService.updateShow(id, requestDTO));
    }

    @Operation(summary = "Delete Show", description = "Remove a show from a screen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Show deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Show not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }
}