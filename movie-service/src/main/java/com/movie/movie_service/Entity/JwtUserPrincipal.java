package com.movie.movie_service.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@Builder
public class JwtUserPrincipal {
    private final String email;
    private final Long userId;
    private final String role;
}
