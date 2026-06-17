package com.movie.movie_service.Repository;

import com.movie.movie_service.Entity.Seat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Page<Seat> findByShowSeatsList_Show_Id(Long showId, Pageable pageable);

    List<Seat> findByShowSeatsList_Show_IdAndShowSeatsList_IsBookedFalse(Long showId);

    Optional<Seat> findByIdAndIsActiveTrue(Long id);

    Seat findByScreen_IdAndRowLabelAndSeatNumber(Long screenId, String rowLabel, String seatNumber);
}
