package com.movie.user_service.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for user login")
public class LoginRequest {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Schema(
            description = "Registered email address of the user",
            example = "sumit@example.com",
            required = true
    )
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Schema(
            description = "Password associated with the account",
            example = "Password@123",
            required = true
    )
    private String password;
}
