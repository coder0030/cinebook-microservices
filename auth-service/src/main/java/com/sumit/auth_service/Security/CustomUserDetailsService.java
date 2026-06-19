package com.sumit.auth_service.Security;

import com.sumit.auth_service.DTO.UserDTO;
import com.sumit.auth_service.Service.UserClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            log.info("Loading user by email: {}", email);
            UserDTO userDTO = userClient.findUserByEmail(email);

            if (userDTO == null) {
                log.error("User not found with email: {}", email);
                throw new UsernameNotFoundException("User not found with email: " + email);
            }

            return User.builder()
                    .username(userDTO.getEmail())
                    .password(userDTO.getPassword())
                    .authorities("ROLE_" + userDTO.getRole().name())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();

        } catch (Exception e) {
            log.error("Error loading user by email: {}", email, e);
            throw new UsernameNotFoundException("User service unavailable. Please try again.");
        }
    }
}