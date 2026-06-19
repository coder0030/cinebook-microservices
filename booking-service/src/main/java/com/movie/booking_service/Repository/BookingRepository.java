package com.movie.booking_service.Repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.movie.booking_service.Entity.Booking;
import com.movie.booking_service.Helper.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    boolean existsByUserIdAndShowIdAndStatusIn(Long userId, Long showId, List<BookingStatus> pending);

    Page<Booking> findByUserId(Long userId, Pageable pageable);

    Page<Booking> findByshowId(Long showId, Pageable pageable);

    Page<Booking> findByStatusIn(List<BookingStatus> pending, Pageable pageable);
}
