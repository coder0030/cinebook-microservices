package com.movie.movie_service.Controller;

import com.movie.movie_service.DTO.MovieDTO;
import com.movie.movie_service.RequestDTO.MovieRequestDTO;
import com.movie.movie_service.Service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
@Tag(name = "Movie operations", description = "APIs for managing movies")
public class MovieController {

    private final MovieService movieService;

    @Operation(summary = "Create a new movie", description = "Add a new movie to the system",
    security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PostMapping("/create")
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieRequestDTO requestDTO) {
        return new ResponseEntity<>(movieService.createMovie(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get Movie by ID", description = "Retrieve a specific movie by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie found successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/public/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @Operation(summary = "Get all movies", description = "Retrieve all movies with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/public/all")
    public ResponseEntity<Page<MovieDTO>> getAllMovies(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(movieService.getAllMovies(pageNo, pageSize));
    }

    @Operation(summary = "Get movies by genre", description = "Retrieve all movies of a specific genre with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Genre not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/public/genre/{genre}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByGenre(
            @PathVariable String genre,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(movieService.getMoviesByGenre(genre, pageNo, pageSize));
    }

    @Operation(summary = "Get movies by language", description = "Retrieve all movies in a specific language with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Language not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/public/language/{language}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByLanguage(
            @PathVariable String language,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(movieService.getMoviesByLanguage(language, pageNo, pageSize));
    }

    @Operation(summary = "Get movies by status", description = "Retrieve all movies with a specific status with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movies retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Status not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/public/status/{status}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(movieService.getMoviesByStatus(status, pageNo, pageSize));
    }

    @Operation(summary = "Update Movie", description = "Update an existing movie by its ID",
            security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(
            @PathVariable Long id,
            @Valid @RequestBody MovieRequestDTO requestDTO) {
        return ResponseEntity.ok(movieService.updateMovie(id, requestDTO));
    }

    @Operation(summary = "Delete Movie", security = @SecurityRequirement(name = "jwtToken"),
            description = "Remove a movie from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Movie deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}