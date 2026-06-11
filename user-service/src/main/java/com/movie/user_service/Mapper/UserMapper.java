package com.movie.user_service.Mapper;

import com.movie.user_service.DTO.UserDTO;
import com.movie.user_service.Entity.User;
import com.movie.user_service.RequestDTO.UserRequestDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User convertRequestDTOToEntity(UserRequestDTO requestDTO, User user) {
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPhoneNumber(requestDTO.getPhoneNumber());
        return user;
    }

    public UserDTO convertEntityToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .password(null)
                .build();
    }
}
