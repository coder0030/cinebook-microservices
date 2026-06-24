package com.movie.movie_service.Controller;

import com.movie.movie_service.DTO.ScreenDTO;
import com.movie.movie_service.RequestDTO.ScreenRequestDTO;
import com.movie.movie_service.Service.ScreenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies/screens")
@RequiredArgsConstructor
@Tag(name = "Screen operations", description = "APIs for managing screens")
public class ScreenController {

    private final ScreenService screenService;

    @Operation(summary = "Create a new screen", security = @SecurityRequirement(name = "jwtToken"),
            description = "Add a new screen to a theatre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Screen created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Theatre not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping("/create")
    public ResponseEntity<ScreenDTO> createScreen(@Valid @RequestBody ScreenRequestDTO requestDTO) {
        return new ResponseEntity<>(screenService.createScreen(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Screen by ID", description = "Retrieve a specific screen by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screen found successfully"),
            @ApiResponse(responseCode = "404", description = "Screen not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/public/{id}")
    public ResponseEntity<ScreenDTO> getScreenById(@PathVariable Long id) {
        return ResponseEntity.ok(screenService.getScreenById(id));
    }

    @Operation(summary = "Get screens by Theatre ID", description = "Retrieve all screens for a specific theatre with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screens retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Theatre not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/public/theatre/{theatreId}")
    public ResponseEntity<Page<ScreenDTO>> getScreensByTheatre(
            @PathVariable Long theatreId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(screenService.getScreensByTheatre(theatreId, pageNo, pageSize));
    }

    @Operation(summary = "Update Screen", security = @SecurityRequirement(name = "jwtToken"),
            description = "Update an existing screen by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screen updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Screen not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ScreenDTO> updateScreen(
            @PathVariable Long id,
            @Valid @RequestBody ScreenRequestDTO requestDTO) {
        return ResponseEntity.ok(screenService.updateScreen(id, requestDTO));
    }

    @Operation(summary = "Delete Screen", security = @SecurityRequirement(name = "jwtToken"),
            description = "Remove a screen from a theatre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Screen deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Screen not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreen(@PathVariable Long id) {
        screenService.deleteScreen(id);
        return ResponseEntity.noContent().build();
    }
}