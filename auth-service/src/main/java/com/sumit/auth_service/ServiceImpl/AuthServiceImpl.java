package com.sumit.auth_service.ServiceImpl;

import com.sumit.auth_service.DTO.AuthResponse;
import com.sumit.auth_service.DTO.UserDTO;
import com.sumit.auth_service.Entity.User;
import com.sumit.auth_service.ExceptionHandler.InvalidCredentialsException;
import com.sumit.auth_service.ExceptionHandler.UserAlreadyExistException;
import com.sumit.auth_service.Helper.Role;
import com.sumit.auth_service.RequestDTO.LoginRequest;
import com.sumit.auth_service.RequestDTO.RegisterUserRequest;
import com.sumit.auth_service.Security.AuthUtil;
import com.sumit.auth_service.Service.AuthService;
import com.sumit.auth_service.Service.UserClient;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;
    private final UserClient userClient;

    @Override
    @CircuitBreaker(name = "authService", fallbackMethod = "authenticateFallback")
    @Retry(name = "authService")
    public AuthResponse authenticate(LoginRequest request) {
        User user = userClient.findUserByEmail(request.getEmail());

        if (user == null || user.getEmail() == null) {
            throw new InvalidCredentialsException("User not found with email: " + request.getEmail());
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = authUtil.generateAccessToken(user);

        user.setUpdatedAt(LocalDateTime.now());
        userClient.saveExistingUser(user);

        return AuthResponse.builder()
                .id(user.getId())
                .jwt(token)
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole())
                .message("Authentication successful")
                .build();
    }

    public AuthResponse authenticateFallback(LoginRequest request, Exception ex) {
        log.error("Fallback for authentication: {}", ex.getMessage());
        return AuthResponse.builder()
                .message("Authentication service is currently unavailable. Please try again later.")
                .build();
    }

    @Override
    @CircuitBreaker(name = "authService", fallbackMethod = "registerUserFallback")
    @Retry(name = "authService")
    public UserDTO registerUser(RegisterUserRequest request) {
        validateNewUserEmail(request.getEmail());
        UserDTO userDTO = convertRequestDTOToUserDTO(request);

        log.info("Sending registration request to user-service for: {}", userDTO.getEmail());
        UserDTO savedUser = userClient.saveUser(userDTO);

        log.info("User registered successfully: {}", savedUser.getEmail());
        return savedUser;
    }

    public UserDTO registerUserFallback(RegisterUserRequest request, Exception ex) {
        log.error("Fallback for registration: {}", ex.getMessage());
        return UserDTO.builder()
                .email(request.getEmail())
                .name(request.getName())
                .message("Registration service is currently unavailable. Please try again later.")
                .build();
    }

    private void validateNewUserEmail(String email) {
        try {
            boolean exists = userClient.existsByEmail(email);
            if (exists) {
                throw new UserAlreadyExistException("User already exists with email: " + email);
            }
        } catch (FeignException.NotFound e) {
            log.info("Email {} not found, proceeding with registration", email);
        } catch (FeignException e) {
            log.error("Feign error while validating email: {}", e.getMessage());
            throw new RuntimeException("Failed to validate email: " + e.getMessage());
        }
    }

    private UserDTO convertRequestDTOToUserDTO(RegisterUserRequest request) {
        return UserDTO.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(Role.USER)
                .isActive(true)
                .build();
    }
}