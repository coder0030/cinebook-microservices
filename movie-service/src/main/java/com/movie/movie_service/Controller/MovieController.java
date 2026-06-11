package com.movie.movie_service.Controller;

import com.movie.movie_service.DTO.MovieDTO;
import com.movie.movie_service.RequestDTO.MovieRequestDTO;
import com.movie.movie_service.Service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/create")
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieRequestDTO requestDTO) {
        return new ResponseEntity<>(movieService.createMovie(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<MovieDTO>> getAllMovies(@RequestParam(defaultValue = "0") int pageNo,
                                                       @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(movieService.getAllMovies(pageNo, pageSize));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByGenre(@PathVariable String genre,
                                                           @RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(movieService.getMoviesByGenre(genre, pageNo, pageSize));
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByLanguage(@PathVariable String language,
                                                              @RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(movieService.getMoviesByLanguage(language, pageNo, pageSize));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<MovieDTO>> getMoviesByStatus(@PathVariable String status,
                                                            @RequestParam(defaultValue = "0") int pageNo,
                                                            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(movieService.getMoviesByStatus(status, pageNo, pageSize));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id, @Valid @RequestBody MovieRequestDTO requestDTO) {
        return ResponseEntity.ok(movieService.updateMovie(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}