package com.movie.booking_service.Service;

import com.movie.booking_service.ServiceImpl.UserClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        fallbackFactory = UserClientFallbackFactory.class
)
public interface UserClient {

    @GetMapping("/api/v1/public/users/{id}/exists")
    Boolean userExists(@PathVariable("id") Long id);
}
