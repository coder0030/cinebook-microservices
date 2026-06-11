package com.movie.movie_service.Controller;

import com.movie.movie_service.DTO.ScreenDTO;
import com.movie.movie_service.RequestDTO.ScreenRequestDTO;
import com.movie.movie_service.Service.ScreenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/screens")
@RequiredArgsConstructor
public class ScreenController {

    private final ScreenService screenService;

    @PostMapping("/create")
    public ResponseEntity<ScreenDTO> createScreen(@Valid @RequestBody ScreenRequestDTO requestDTO) {
        return new ResponseEntity<>(screenService.createScreen(requestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScreenDTO> getScreenById(@PathVariable Long id) {
        return ResponseEntity.ok(screenService.getScreenById(id));
    }

    @GetMapping("/theatre/{theatreId}")
    public ResponseEntity<Page<ScreenDTO>> getScreensByTheatre(@PathVariable Long theatreId,
                                                               @RequestParam(defaultValue = "0") int pageNo,
                                                               @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(screenService.getScreensByTheatre(theatreId, pageNo, pageSize));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScreenDTO> updateScreen(@PathVariable Long id, @Valid @RequestBody ScreenRequestDTO requestDTO) {
        return ResponseEntity.ok(screenService.updateScreen(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteScreen(@PathVariable Long id) {
        screenService.deleteScreen(id);
        return ResponseEntity.noContent().build();
    }
}