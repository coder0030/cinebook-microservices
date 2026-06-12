package com.movie.movie_service.RequestDTO;

import com.movie.movie_service.Helper.ScreenType;
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
@Schema(description = "Request object for creating/updating a screen in a theatre")
public class ScreenRequestDTO {

    @NotBlank(message = "Screen name is required")
    @Schema(description = "Name of the screen", example = "Screen 1", required = true, minLength = 1, maxLength = 50)
    private String screenName;

    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    @Max(value = 500, message = "Total seats cannot exceed 500")
    @Schema(description = "Total number of seats in the screen", example = "150", required = true, minimum = "1", maximum = "500")
    private Integer totalSeats;

    @NotNull(message = "Screen type is required")
    @Schema(description = "Type of screen technology", required = true,
            example = "STANDARD", allowableValues = {"STANDARD", "IMAX", "4DX", "3D", "VIP", "DOLBY"})
    private ScreenType screenType;

    @NotNull(message = "Theatre ID is required")
    @Schema(description = "ID of the theatre this screen belongs to", example = "5", required = true, minimum = "1")
    private Long theatreId;
}