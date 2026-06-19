package com.movie.movie_service.Repository;

import com.movie.movie_service.Entity.ShowSeat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {
    Optional<ShowSeat> findByIdAndIsActiveTrue(Long id);

    Page<ShowSeat> findByShow_IdAndIsActiveTrue(Long showId, Pageable pageable);

    Page<ShowSeat> findByShow_IdAndSeat_IsBookedFalse(Long showId, Pageable pageable);

    ShowSeat findBySeat_IdAndShow_IdAndIsActiveTrue(Long seatId, Long showId);
}
