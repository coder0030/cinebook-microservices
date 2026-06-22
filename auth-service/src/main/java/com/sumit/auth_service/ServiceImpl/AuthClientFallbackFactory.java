package com.sumit.auth_service.ServiceImpl;

import com.sumit.auth_service.DTO.UserDTO;
import com.sumit.auth_service.Service.UserClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthClientFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable cause) {
        log.error("Feign call failed: ", cause);

        return new UserClient() {
            @Override
            public UserDTO findUserByEmail(String email) {
                log.error("Fallback: findUserByEmail for email: {}, error: {}", email, cause.getMessage());
                return null;
            }

            @Override
            public UserDTO findUserById(Long id) {
                log.error("Fallback: findUserById for id: {}, error: {}", id, cause.getMessage());
                return null;
            }

            @Override
            public boolean existsByEmailAndIdNot(String email, Long id) {
                log.error("Fallback: existsByEmailAndIdNot for email: {}, id: {}, error: {}", email, id, cause.getMessage());
                return false;
            }

            @Override
            public UserDTO saveUser(UserDTO userDTO) {
                log.error("Fallback: saveUser for email: {}, error: {}", userDTO.getEmail(), cause.getMessage());
                throw new RuntimeException("User service is currently unavailable. Please try again later.");
            }

            @Override
            public boolean existsByEmail(String email) {
                log.error("Fallback: existsByEmailAndIdNot for email: {}, error: {}", email, cause.getMessage());
                return false;
            }
        };
    }
}