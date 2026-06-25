package com.movie.user_service.Repository;

import com.movie.user_service.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndIsActiveTrue(String email);

    User findByIdAndIsActiveTrue(Long userId);

    boolean existsByEmailAndIsActiveTrueAndIdNot(String email, Long id);

    boolean existsByEmailAndIsActiveTrue(String email);

    Boolean existsByIdAndIsActiveTrue(Long userId);

    Optional<User> findByEmail(String email);

    User findByNameAndIsActiveTrue(String name);
}
