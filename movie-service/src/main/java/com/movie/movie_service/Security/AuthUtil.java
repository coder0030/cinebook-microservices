package com.movie.movie_service.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims extractToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("Token extraction failed: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isValidToken(String token) {
        try {
            return extractToken(token).getExpiration().after(new Date(System.currentTimeMillis()));
        } catch (Exception e) {
            log.warn("Invalid token: {}", e.getMessage());
            return false;
        }
    }

    public String getEmail(String token) {
        Claims claims = extractToken(token);
        return claims.get("email").toString();
    }

    public String getRole(String token) {
        Claims claims = extractToken(token);
        return claims.get("roles").toString();
    }

    public String getUserId(String token) {
        Claims claims = extractToken(token);
        return claims.getSubject().toString();
    }
}