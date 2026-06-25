package com.movie.movie_service.Repository;

import com.movie.movie_service.Entity.Show;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ShowRepository extends JpaRepository<Show, Long> {
    Optional<Show> findByIdAndIsActiveTrue(Long id);
    
    Page<Show> findByTheatre_IdAndIsActiveTrue(Long theatreId, Pageable pageable);

    List<Show> findByMovie_IdAndIsActiveTrue(Long movieId);

    Page<Show> findByScreen_IdAndShowDateAndIsActiveTrue(Long screenId, LocalDate date, Pageable pageable);

    Page<Show> findByShowDateAndIsActiveTrue(LocalDate date, Pageable pageable);



    @Query("""
SELECT s FROM Show s
WHERE s.screen.id = :screenId
AND s.showDate = :showDate
AND s.isActive = true
AND (:excludeId IS NULL OR s.id <> :excludeId)
AND :startTime < s.endTime
AND :endTime > s.showTime
""")
    List<Show> findOverlappingShows(
            @Param("screenId") Long screenId,
            @Param("showDate") LocalDate showDate,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime,
            @Param("excludeId") Long excludeId
    );

    boolean existsByIdAndIsActiveTrue(Long showId);
}
