package com.movie.movie_service.Controller;

import com.movie.movie_service.DTO.TheatreDTO;
import com.movie.movie_service.RequestDTO.TheatreRequestDTO;
import com.movie.movie_service.Service.TheatreService;
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
@RequestMapping("/movies/theatres")
@RequiredArgsConstructor
@Tag(name = "Theatre operations", description = "APIs for theatre registration, update, and other operations.")
public class TheatreController {

    private final TheatreService theatreService;

    @Operation(summary = "Create new Theatre", description = "Create a new Theatre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Theatre successfully created."),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request"),
            @ApiResponse(responseCode = "403", description = "User not authenticated for this operation."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping("/create")
    public ResponseEntity<TheatreDTO> createTheatre(@Valid @RequestBody TheatreRequestDTO requestDTO) {
        return new ResponseEntity<>(theatreService.createTheatre(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Theatre by ID", security = @SecurityRequirement(name = "jwtToken"),
            description = "Retrieve a specific theatre by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Theatre found successfully"),
            @ApiResponse(responseCode = "404", description = "Theatre not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/public/{id}")
    public ResponseEntity<TheatreDTO> getTheatreById(@PathVariable Long id) {
        return ResponseEntity.ok(theatreService.getTheatreById(id));
    }

    @Operation(summary = "Get all Theatres", description = "Retrieve all theatres with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Theatres retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/public/all")
    public ResponseEntity<Page<TheatreDTO>> getAllTheatres(@RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(theatreService.getAllTheatres(pageNo, pageSize));
    }

    @Operation(summary = "Get Theatres by City", description = "Retrieve all theatres in a specific city with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Theatres retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No theatres found in this city"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/public/city/{city}")
    public ResponseEntity<Page<TheatreDTO>> getTheatresByCity(@PathVariable String city,
                                                              @RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(theatreService.getTheatresByCity(city, pageNo, pageSize));
    }

    @Operation(summary = "Update Theatre", security = @SecurityRequirement(name = "jwtToken"),
            description = "Update an existing theatre by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Theatre updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid credentials"),
            @ApiResponse(responseCode = "404", description = "Theatre not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request"),
            @ApiResponse(responseCode = "403", description = "User not authenticated for this operation."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TheatreDTO> updateTheatre(@PathVariable Long id, @Valid @RequestBody TheatreRequestDTO requestDTO) {
        return ResponseEntity.ok(theatreService.updateTheatre(id, requestDTO));
    }

    @Operation(summary = "Delete Theatre", security = @SecurityRequirement(name = "jwtToken"),
            description = "Delete a theatre by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Theatre deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Theatre not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request"),
            @ApiResponse(responseCode = "403", description = "User not authenticated for this operation."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheatre(@PathVariable Long id) {
        theatreService.deleteTheatre(id);
        return ResponseEntity.noContent().build();
    }
}