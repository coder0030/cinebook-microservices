package com.movie.user_service.Security;

import com.movie.user_service.DTO.AuthResponse;
import com.movie.user_service.Entity.User;
import com.movie.user_service.ExceptionHandler.InvalidCredentialsException;
import com.movie.user_service.ExceptionHandler.UserNotFoundException;
import com.movie.user_service.Repository.UserRepository;
import com.movie.user_service.RequestDTO.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtil authUtil;

    public AuthResponse authenticate(LoginRequest request) {

        User user = userRepository.findByEmailAndIsActiveTrue(request.getEmail());

        if (user == null ||
                !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = authUtil.generateAccessToken(user);

        return AuthResponse.builder()
                .id(user.getId())
                .jwt(token)
                .build();
    }
}
