package com.sumit.auth_service.ServiceImpl;

import com.sumit.auth_service.DTO.UserDTO;
import com.sumit.auth_service.Service.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthClientFallback implements UserClient {

    @Override
    public UserDTO findUserByEmail(String email) {
        log.warn("User Service fallback: findUserByEmail for email: {}", email);
        throw new RuntimeException("User service is currently unavailable");
    }

    @Override
    public UserDTO findUserById(Long id) {
        log.warn("User Service fallback: findUserById for id: {}", id);
        throw new RuntimeException("User service is currently unavailable");
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        log.warn("User Service fallback: existsByEmailAndIdNot for email: {}, id: {}", email, id);
        return false;
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        log.warn("User Service fallback: saveUser for email: {}", userDTO.getEmail());
        throw new RuntimeException("User service is currently unavailable");
    }
}