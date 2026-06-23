package com.sumit.auth_service.ServiceImpl;

import com.sumit.auth_service.DTO.UserDTO;
import com.sumit.auth_service.Entity.User;
import com.sumit.auth_service.Service.UserClient;
import org.springframework.cloud.openfeign.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthClientFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable cause) {
        log.error("Feign communication gateway failed. Root cause: ", cause);

        return new UserClient() {
            @Override
            public User findUserByEmail(String email) {
                log.error("Fallback: findUserByEmail failed for: {}. Reason: {}", email, cause.getMessage());
                return null;
            }

            @Override
            public UserDTO findUserById(Long id) {
                log.error("Fallback: findUserById failed for ID: {}. Reason: {}", id, cause.getMessage());
                return null;
            }

            @Override
            public boolean existsByEmailAndIdNot(String email, Long id) {
                log.error("Fallback: existsByEmailAndIdNot failed. Reason: {}", cause.getMessage());
                return false;
            }

            @Override
            public UserDTO saveUser(UserDTO userDTO) {
                log.error("Fallback: saveUser transaction failed for email: {}. Reason: {}", userDTO.getEmail(), cause.getMessage());
                return UserDTO.builder()
                        .email(userDTO.getEmail())
                        .message("User background profile engine is currently offline.")
                        .build();
            }

            @Override
            public boolean existsByEmail(String email) {
                log.error("Fallback: existsByEmail failed. Reason: {}", cause.getMessage());
                return false;
            }

            @Override
            public User saveExistingUser(User user) {
              return null;
            }
        };
    }
}