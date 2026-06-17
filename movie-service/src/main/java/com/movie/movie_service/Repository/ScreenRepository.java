package com.movie.movie_service.Repository;

import com.movie.movie_service.Entity.Screen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScreenRepository extends JpaRepository<Screen, Long> {
    Page<Screen> findByTheatreIdAndIsActiveTrue(Long theatreId, Pageable pageable);

    Optional<Screen> findByIdAndIsActiveTrue(Long id);
}
