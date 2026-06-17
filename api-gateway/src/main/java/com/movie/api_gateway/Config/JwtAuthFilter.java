package com.movie.api_gateway.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {
    @Value("${jwt.secret}")
    private String secretKey;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/users/auth/login",
            "/users/auth/register",
            "/actuator/health",
            "/actuator/info"
    );

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if(isPublicPath(path)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return sendError(exchange, HttpStatus.UNAUTHORIZED, "Token missing");
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = extractClaims(token);

            if (claims.getExpiration().before(new Date())) {
                return sendError(exchange, HttpStatus.UNAUTHORIZED, "Token expired");
            }

            ServerHttpRequest modifiedRequest = request.mutate()
                    .header("X-User-Id", claims.getSubject())
                    .header("X-User-Role", claims.get("roles", String.class))
                    .header("X-User-Email", claims.get("email", String.class))
                    .build();

            return chain.filter(exchange.mutate().request(modifiedRequest).build());
        } catch (JwtException e) {
            return sendError(exchange, HttpStatus.UNAUTHORIZED, "Invalid token");
        }
    }

    private Claims extractClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::contains);
    }

    private Mono<Void> sendError(ServerWebExchange exchange,
                                 HttpStatus status, String message) {

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().add("Content-Type", "application/json");

        byte[] bytes = ("{\"error\": \"" + message + "\"}").getBytes();
        DataBuffer buffer = exchange.getResponse()
                .bufferFactory().wrap(bytes);

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}









