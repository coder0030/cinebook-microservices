package com.sumit.auth_service.RequestDTO;

import com.sumit.auth_service.Helper.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User Request for registration.")
public class RegisterUserRequest {

    @Schema(description = "Name is required for registering", maxLength = 50, minLength = 3, example = "Sumit")
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @Schema(description = "Email is required for registering", example = "sumit@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Password is required for registering", minLength = 6, example = "password123")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Schema(description = "Phone number of the user", example = "9876543210")
    private String phoneNumber;

    @Schema(description = "Role of the user", example = "USER", allowableValues = {"USER", "ADMIN"})
    @Enumerated(EnumType.STRING)
    private Role role;
}