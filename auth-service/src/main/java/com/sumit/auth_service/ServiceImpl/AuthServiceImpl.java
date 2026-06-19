package com.sumit.auth_service.ServiceImpl;

import com.sumit.auth_service.DTO.AuthResponse;
import com.sumit.auth_service.DTO.UserDTO;
import com.sumit.auth_service.ExceptionHandler.InvalidCredentialsException;
import com.sumit.auth_service.ExceptionHandler.UserAlreadyExistException;
import com.sumit.auth_service.ExceptionHandler.UserNotFoundException;
import com.sumit.auth_service.Helper.Role;
import com.sumit.auth_service.RequestDTO.LoginRequest;
import com.sumit.auth_service.RequestDTO.RegisterUserRequest;
import com.sumit.auth_service.Security.AuthUtil;
import com.sumit.auth_service.Service.AuthService;
import com.sumit.auth_service.Service.UserClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        try {
            UserDTO user = userClient.findUserByEmail(request.getEmail());

            if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new InvalidCredentialsException("Invalid email or password");
            }

            String token = authUtil.generateAccessToken(user);

            return AuthResponse.builder()
                    .id(user.getId())
                    .jwt(token)
                    .email(user.getEmail())
                    .name(user.getName())
                    .role(user.getRole())
                    .build();

        } catch (Exception e) {
            log.error("Error during authentication: {}", e.getMessage());
            throw new InvalidCredentialsException("Authentication failed. Please try again.");
        }
    }

    public AuthResponse authenticateFallback(LoginRequest request, Exception ex) {
        log.error("Fallback executed for authenticate with email: {}, error: {}",
                request.getEmail(), ex.getMessage());

        return AuthResponse.builder()
                .message("Authentication service is currently unavailable. Please try again later.")
                .jwt(null)
                .build();
    }

    @Override
    @CircuitBreaker(name = "authService", fallbackMethod = "registerUserFallback")
    @Retry(name = "authService")
    public UserDTO registerUser(RegisterUserRequest request) {
        try {
            validateRequest(request.getEmail(), null);
            UserDTO userDTO = convertRequestDTOToUserDTO(request);
            if (userDTO.getRole() == null) {
                userDTO.setRole(Role.ROLE_USER);
            }

            UserDTO savedUser = userClient.saveUser(userDTO);
            log.info("User registered successfully with ID: {}", savedUser.getId());

            return savedUser;

        } catch (Exception e) {
            log.error("Error during registration: {}", e.getMessage());
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    public UserDTO registerUserFallback(RegisterUserRequest request, Exception ex) {
        log.error("Fallback executed for registerUser with email: {}, error: {}",
                request.getEmail(), ex.getMessage());

        return UserDTO.builder()
                .email(request.getEmail())
                .name(request.getName())
                .message("Registration service is currently unavailable. Please try again later.")
                .build();
    }

    private void validateRequest(String email, Long id) {
        if (email != null && userClient.existsByEmailAndIdNot(email, id)) {
            throw new UserAlreadyExistException("User already exists with email: " + email);
        }
    }

    private UserDTO convertRequestDTOToUserDTO(RegisterUserRequest request) {
        return UserDTO.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .role(request.getRole())
                .isActive(true)
                .build();
    }
}