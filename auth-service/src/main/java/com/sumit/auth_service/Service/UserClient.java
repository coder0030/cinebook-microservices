package com.sumit.auth_service.Service;

import com.sumit.auth_service.DTO.UserDTO;
import com.sumit.auth_service.ServiceImpl.AuthClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "user-service",
        fallback = AuthClientFallback.class
)
public interface UserClient {

    @GetMapping("/users/email/{email}")
    UserDTO findUserByEmail(@PathVariable("email") String email);

    @GetMapping("/users/{id}")
    UserDTO findUserById(@PathVariable("id") Long id);

    @GetMapping("users/email/{email}/id/{id}/not")
    boolean existsByEmailAndIdNot(@PathVariable("email") String email,
                                  @PathVariable("id") Long id);

    @PostMapping("/users/register")
    UserDTO saveUser(@RequestBody UserDTO userDTO);
}