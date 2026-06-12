package com.movie.movie_service.Repository;

import com.movie.movie_service.Entity.Theatre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {
    Optional<Theatre> findByIdAndIsActiveTrue(Long id);

    Page<Theatre> findByCityAndIsActiveTrue(String city, Pageable pageable);

    boolean existsByNameAndCityAndStateAndIsActiveTrueAndIdNot(String name, String city, String state, Long id);

    Page<Theatre> findByIsActiveTrue(Pageable pageable);
}
