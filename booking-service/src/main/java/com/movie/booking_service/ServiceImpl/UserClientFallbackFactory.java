package com.movie.booking_service.ServiceImpl;


import com.movie.booking_service.Service.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@Slf4j
public class UserClientFallbackFactory implements FallbackFactory {

    @Override
    public Object create(Throwable cause) {
        log.error("Feign communication gateway failed. Root cause: ", cause);

        return  new UserClient() {
            @Override
            public Boolean userExists(@PathVariable("id") Long id) {
                log.error("Fallback UserExists failed with id: {}, reason: {} ", id, cause.getMessage());
                return false;
            }
        };
    }
}