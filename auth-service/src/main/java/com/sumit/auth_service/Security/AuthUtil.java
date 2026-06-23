package com.sumit.auth_service.Security;

import com.sumit.auth_service.DTO.UserDTO;
import com.sumit.auth_service.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class AuthUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRole());
        claims.put("email", user.getEmail());
        claims.put("name", user.getName());

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 90 * 60 * 1000)) // 90 min
                .signWith(getSecretKey())
                .compact();
    }

    public Claims extractToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUserNameFronToken(String token) {
        Claims claims = extractToken(token);
        return claims.get("name").toString();
    }

    public String getUserEmailFronToken(String token) {
        Claims claims = extractToken(token);
        return claims.get("email").toString();
    }

    public String getUserRolesFromToken(String token) {
        Claims claims = extractToken(token);
        return claims.get("roles").toString();
    }

    public boolean isValidToken(String token) {
        if(extractToken(token).getExpiration().after(new Date(
                System.currentTimeMillis()
        ))) {
            return true;
        }

        return false;
    }
}
