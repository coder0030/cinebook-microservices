package com.movie.user_service.Repository;

import com.movie.user_service.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmailAndIsActiveTrue(String email);

    User findByIdAndIsActiveTrue(Long userId);

    boolean existsByEmailAndIsActiveTrueAndIdNot(String email, Long id);
}
