package com.movie.booking_service.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class JwtUserPrincipal {
    private final String email;
    private final Long userId;
    private final String role;
}
