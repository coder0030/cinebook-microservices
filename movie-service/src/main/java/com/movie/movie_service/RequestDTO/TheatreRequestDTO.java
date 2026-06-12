package com.movie.movie_service.RequestDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request object for creating/updating a theatre")
public class TheatreRequestDTO {

    @NotBlank(message = "Theatre name is required")
    @Size(min = 2, max = 100, message = "Theatre name must be between 2 and 100 characters")
    @Schema(description = "Name of the theatre", example = "PVR Cinemas", required = true, minLength = 2, maxLength = 100)
    private String name;

    @NotBlank(message = "Address is required")
    @Schema(description = "Complete street address of the theatre", example = "123 Main Street, Andheri West", required = true, maxLength = 255)
    private String address;

    @NotBlank(message = "City is required")
    @Schema(description = "City where theatre is located", example = "Mumbai", required = true, maxLength = 50)
    private String city;

    @NotBlank(message = "State is required")
    @Schema(description = "State where theatre is located", example = "Maharashtra", required = true, maxLength = 50)
    private String state;

    @Min(value = 1, message = "Total screens must be at least 1")
    @Max(value = 50, message = "Total screens cannot exceed 50")
    @Schema(description = "Total number of screens in the theatre", example = "5", required = true, minimum = "1", maximum = "50")
    private Integer totalScreens;
}