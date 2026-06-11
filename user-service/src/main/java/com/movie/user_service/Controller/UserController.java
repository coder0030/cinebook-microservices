package com.movie.user_service.Controller;

import com.movie.user_service.DTO.BookingDTO;
import com.movie.user_service.DTO.UserDTO;
import com.movie.user_service.RequestDTO.LoginRequestDTO;
import com.movie.user_service.RequestDTO.UpdateUserRequestDTO;
import com.movie.user_service.RequestDTO.UserRequestDTO;
import com.movie.user_service.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/users")
@Tag(
        name = "User Service",
        description = "APIs for user registration, profile management, and ticket booking operations."
)
public class UserController {

    private final UserService userService;

    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User has registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failed"),
            @ApiResponse(responseCode = "409", description = "User already exists"),
            @ApiResponse(responseCode = "415", description = "Unsupported media type"),
            @ApiResponse(responseCode = "500", description = "Internal server error")

    })
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        log.info("Registering new user with email: {}", requestDTO.getEmail());

        UserDTO userResponse = userService.registerUser(requestDTO);
        return new ResponseEntity<>(userResponse,HttpStatus.CREATED);
    }

    @Operation(summary = "Log-In")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has logged in successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failed"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "415", description = "Unsupported media type"),
            @ApiResponse(responseCode = "500", description = "Internal server error")

    })
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDTO loginRequest) {
        String response = userService.loginUser(loginRequest);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failed"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "403", description = "You're not allowed to perform this operation")

    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Update user information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has updated his information successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failed"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "415", description = "Unsupported media type"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "403", description = "You're not allowed to perform this operation")

    })
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequestDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(userId, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Delete user by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failed"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "403", description = "You're not allowed to perform this operation")

    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

//    @Operation(summary = "Get user bookings by Id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "User bookings fetched successfully"),
//            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failed"),
//            @ApiResponse(responseCode = "404", description = "User not found"),
//            @ApiResponse(responseCode = "500", description = "Internal server error"),
//            @ApiResponse(responseCode = "403", description = "You're not allowed to perform this operation")
//
//    })
//    @GetMapping("/{userId}/bookings")
//    public ResponseEntity<Page<BookingDTO>> getUserBookings(@RequestParam(defaultValue = "0") int pageNo,
//                                                            @RequestParam(defaultValue = "20") int size,
//                                                            @PathVariable Long userId) {
//        Page<BookingDTO> bookings = userService.getUserBookings(userId, pageNo, size);
//        return ResponseEntity.ok(bookings);
//    }

//    @Operation(summary = "Get user by Id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "User cancellation successfully"),
//            @ApiResponse(responseCode = "400", description = "Invalid request payload or validation failed"),
//            @ApiResponse(responseCode = "404", description = "User not found"),
//            @ApiResponse(responseCode = "500", description = "Internal server error"),
//            @ApiResponse(responseCode = "403", description = "You're not allowed to perform this operation")
//
//    })
//    @PutMapping("/{userId}/bookings/{bookingId}/cancel")
//    public ResponseEntity<String> cancelBooking(@PathVariable Long userId, @PathVariable Long bookingId) {
//        userService.cancelBooking(userId, bookingId);
//        return ResponseEntity.ok("Booking cancelled successfully");
//    }
}
