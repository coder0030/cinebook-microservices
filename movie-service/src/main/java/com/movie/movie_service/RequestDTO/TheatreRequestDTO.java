package com.movie.movie_service.RequestDTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheatreRequestDTO {

    @NotBlank(message = "Theatre name is required")
    @Size(min = 2, max = 100, message = "Theatre name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @Min(value = 1, message = "Total screens must be at least 1")
    @Max(value = 50, message = "Total screens cannot exceed 50")
    private Integer totalScreens;
}