package com.movie.user_service.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for user registration")
public class UserRequestDTO {

    @NotBlank(message = "Name cannot be blank")
    @Schema(description = "Full name of the user", example = "John Doe", required = true)
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Schema(description = "Password for the account (min 6 characters)", example = "Password123", required = true, minLength = 6)
    private String password;

    @NotBlank(message = "Phone number cannot be blank")
    @Schema(description = "10-digit mobile number", example = "9876543210", required = true, pattern = "^[0-9]{10}$")
    private String phoneNumber;
}