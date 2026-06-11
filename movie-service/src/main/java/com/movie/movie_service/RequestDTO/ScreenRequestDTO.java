package com.movie.movie_service.RequestDTO;

import com.movie.movie_service.Helper.ScreenType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenRequestDTO {

    @NotBlank(message = "Screen name is required")
    private String screenName;

    @NotNull(message = "Total seats is required")
    @Min(value = 1, message = "Total seats must be at least 1")
    @Max(value = 500, message = "Total seats cannot exceed 500")
    private Integer totalSeats;

    @NotNull(message = "Screen type is required")
    private ScreenType screenType;

    @NotNull(message = "Theatre ID is required")
    private Long theatreId;
}