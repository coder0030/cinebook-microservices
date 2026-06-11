package com.movie.user_service.Service;

import com.movie.user_service.DTO.BookingDTO;
import com.movie.user_service.DTO.UserDTO;
import com.movie.user_service.RequestDTO.LoginRequestDTO;
import com.movie.user_service.RequestDTO.UpdateUserRequestDTO;
import com.movie.user_service.RequestDTO.UserRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface UserService {
    UserDTO registerUser(@Valid UserRequestDTO requestDTO);

    String loginUser(LoginRequestDTO loginRequest);

    UserDTO getUserById(Long userId);

    UserDTO updateUser(Long userId, UpdateUserRequestDTO userDTO);

    void deleteUser(Long userId);

   // void cancelBooking(Long userId, Long bookingId);

   // Page<BookingDTO> getUserBookings(Long userId, int pageNo, int size);
}
