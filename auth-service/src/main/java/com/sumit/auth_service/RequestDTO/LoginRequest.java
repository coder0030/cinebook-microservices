package com.sumit.auth_service.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request object for user login")
public class LoginRequest {

    @Schema(description = "Registered email address of the user", example = "sumit@example.com", required = true)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Password associated with the account", example = "Password123", required = true)
    @NotBlank(message = "Password cannot be blank")
    private String password;
}