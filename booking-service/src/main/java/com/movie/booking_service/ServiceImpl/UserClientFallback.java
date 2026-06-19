package com.movie.booking_service.ServiceImpl;

import com.movie.booking_service.ExceptionHandler.ServiceUnavailableException;
import com.movie.booking_service.Service.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserClientFallback implements UserClient {

    @Override
    public Boolean userExists(Long id) {
        log.warn("User Service fallback: userExists for id: {}", id);
        throw new ServiceUnavailableException("User service is currently unavailable");
    }
}