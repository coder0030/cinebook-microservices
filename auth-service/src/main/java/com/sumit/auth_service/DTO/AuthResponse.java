package com.sumit.auth_service.DTO;

import com.sumit.auth_service.Helper.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private Long id;
    private String jwt;
    private String name;
    private String email;
    private Role role;
    private String message;
}