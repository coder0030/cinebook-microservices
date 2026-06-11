package com.movie.movie_service.Controller;

import com.movie.movie_service.DTO.TheatreDTO;
import com.movie.movie_service.RequestDTO.TheatreRequestDTO;
import com.movie.movie_service.Service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/theatres")
@RequiredArgsConstructor
public class TheatreController {

    private final TheatreService theatreService;

    @PostMapping("/create")
    public ResponseEntity<TheatreDTO> createTheatre(@Valid @RequestBody TheatreRequestDTO requestDTO) {
        return new ResponseEntity<>(theatreService.createTheatre(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheatreDTO> getTheatreById(@PathVariable Long id) {
        return ResponseEntity.ok(theatreService.getTheatreById(id));
    }

    @GetMapping
    public ResponseEntity<Page<TheatreDTO>> getAllTheatres(@RequestParam(defaultValue = "0") int pageNo,
                                                           @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(theatreService.getAllTheatres(pageNo, pageSize));
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<Page<TheatreDTO>> getTheatresByCity(@PathVariable String city,
                                                              @RequestParam(defaultValue = "0") int pageNo,
                                                              @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(theatreService.getTheatresByCity(city));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TheatreDTO> updateTheatre(@PathVariable Long id, @Valid @RequestBody TheatreRequestDTO requestDTO) {
        return ResponseEntity.ok(theatreService.updateTheatre(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheatre(@PathVariable Long id) {
        theatreService.deleteTheatre(id);
        return ResponseEntity.noContent().build();
    }
}