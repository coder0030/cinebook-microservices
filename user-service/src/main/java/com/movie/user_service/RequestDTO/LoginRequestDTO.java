package com.movie.user_service.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for user login")
public class LoginRequestDTO {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Schema(description = "Registered email address", example = "john.doe@example.com", required = true)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Schema(description = "Account password", example = "password123", required = true, format = "password")
    private String password;
}