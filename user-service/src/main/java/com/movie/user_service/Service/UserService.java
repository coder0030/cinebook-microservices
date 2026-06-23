package com.movie.user_service.Service;

import com.movie.user_service.DTO.UserDTO;
import com.movie.user_service.Entity.User;
import com.movie.user_service.RequestDTO.UpdateUserRequestDTO;
import com.movie.user_service.RequestDTO.UserRequestDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UpdateUserRequestDTO userDTO);

    void deleteUser(Long userId);

    Boolean checkUserExists(Long userId);

    User getUserByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByEmail(String email);

    User findByEmail(String email);

    User saveUser(User user);

    User saveExistingUser(User user);
}
