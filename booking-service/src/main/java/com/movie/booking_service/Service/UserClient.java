package com.movie.booking_service.Service;

import com.movie.booking_service.ServiceImpl.UserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        fallback = UserClientFallback.class
)
public interface UserClient {

    @GetMapping("/users/{id}/exists")
    Boolean userExists(@PathVariable("id") Long id);
}
