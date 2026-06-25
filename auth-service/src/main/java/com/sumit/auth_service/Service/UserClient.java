package com.sumit.auth_service.Service;

import com.sumit.auth_service.DTO.UserDTO;
import com.sumit.auth_service.Entity.User;
import com.sumit.auth_service.ServiceImpl.AuthClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "user-service",
        fallbackFactory = AuthClientFallbackFactory.class
)
public interface UserClient {

    @GetMapping("/api/v1/users/public/email/{email}")
    User findUserByEmail(@PathVariable("email") String email);

    @GetMapping("/api/v1/users/public/{id}")
    UserDTO findUserById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/users/public/email/{email}/id/{id}/not")
    boolean existsByEmailAndIdNot(@PathVariable("email") String email,
                                  @PathVariable("id") Long id);

    @PostMapping("/api/v1/users/public/register")
    UserDTO saveUser(@RequestBody UserDTO userDTO);

    @GetMapping("/api/v1/users/public/exists/email/{email}")
    boolean existsByEmail(@PathVariable("email") String email);

    @PostMapping("/api/v1/users/existing")
    User saveExistingUser(@RequestBody User user);
}