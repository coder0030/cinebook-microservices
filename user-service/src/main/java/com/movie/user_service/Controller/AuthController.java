package com.movie.user_service.Controller;

import com.movie.user_service.DTO.AuthResponse;
import com.movie.user_service.DTO.UserDTO;
import com.movie.user_service.RequestDTO.LoginRequest;
import com.movie.user_service.RequestDTO.UserRequestDTO;
import com.movie.user_service.Security.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/auth/")
@Tag(
        name = "Auth Service",
        description = "APIs for user registration and login operations."
)
public class AuthController {

    private final AuthService authService;
    private final UserController userController;


    @Operation(summary = "Login a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User has registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failed"),
            @ApiResponse(responseCode = "409", description = "User already exists"),
            @ApiResponse(responseCode = "415", description = "Unsupported media type"),
            @ApiResponse(responseCode = "500", description = "Internal server error")

    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User has registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failed"),
            @ApiResponse(responseCode = "409", description = "User already exists"),
            @ApiResponse(responseCode = "415", description = "Unsupported media type"),
            @ApiResponse(responseCode = "500", description = "Internal server error")

    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserRequestDTO request) {
        UserDTO userDTO = userController.registerUser(request).getBody();
        return ResponseEntity.ok(userDTO);
    }
}
