package com.movie.user_service.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for updating user details (all fields optional)")
public class UpdateUserRequestDTO {

    @Email(message = "Invalid email format")
    @Schema(description = "Updated email address", example = "john.new@example.com", required = false)
    private String email;

    @Schema(description = "Updated full name", example = "Johnathan Doe", required = false)
    private String name;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    @Schema(description = "Updated 10-digit mobile number", example = "9988776655", required = false, pattern = "^[0-9]{10}$")
    private String phoneNumber;

    @Schema(description = "New password", example = "newPassword123", required = false, minLength = 6)
    private String password;
}