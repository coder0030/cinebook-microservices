package com.sumit.auth_service.Service;

import com.sumit.auth_service.DTO.AuthResponse;
import com.sumit.auth_service.DTO.UserDTO;
import com.sumit.auth_service.RequestDTO.LoginRequest;
import com.sumit.auth_service.RequestDTO.RegisterUserRequest;

public interface AuthService {
    AuthResponse authenticate(LoginRequest request);

    UserDTO registerUser(RegisterUserRequest request);
}
