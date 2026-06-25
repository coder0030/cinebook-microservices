package com.movie.payment_service.Controller;

import com.movie.payment_service.DTO.PaymentResponseDTO;
import com.movie.payment_service.Helper.PaymentStatus;
import com.movie.payment_service.RequestDTO.PaymentRequestDTO;
import com.movie.payment_service.Service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payment operations", description = "APIs for managing payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Process a new payment", description = "Process payment for a booking",
            security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Payment processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "404", description = "Booking not found"),
            @ApiResponse(responseCode = "409", description = "Payment already processed"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/process")
    public ResponseEntity<PaymentResponseDTO> processPayment(@Valid @RequestBody PaymentRequestDTO requestDTO) {
        return new ResponseEntity<>(paymentService.processPayment(requestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Get payment by ID", description = "Retrieve a specific payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment found successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/public/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @Operation(summary = "Get payment by booking ID", description = "Retrieve payment details for a specific booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment found successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found for the booking"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/public/booking/{bookingId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByBookingId(@PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentByBookingId(bookingId));
    }

    @Operation(summary = "Get all payments for a user", description = "Retrieve all payments for a specific user with pagination",
            security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PaymentResponseDTO>> getPaymentsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(paymentService.getPaymentsByUserId(userId, pageNo, pageSize));
    }

    @Operation(summary = "Get payments by status", description = "Retrieve all payments with a specific status with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Status not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/public/status/{status}")
    public ResponseEntity<Page<PaymentResponseDTO>> getPaymentsByStatus(
            @PathVariable PaymentStatus status,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(paymentService.getPaymentsByStatus(status, pageNo, pageSize));
    }

    @Operation(summary = "Update payment status", description = "Update the status of a payment",
            security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status transition"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{paymentId}/status")
    public ResponseEntity<PaymentResponseDTO> updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestParam PaymentStatus status) {
        return ResponseEntity.ok(paymentService.updatePaymentStatus(paymentId, status));
    }

    @Operation(summary = "Refund payment", description = "Process a refund for a completed payment",
            security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment refunded successfully"),
            @ApiResponse(responseCode = "400", description = "Payment cannot be refunded"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{paymentId}/refund")
    public ResponseEntity<PaymentResponseDTO> refundPayment(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.refundPayment(paymentId));
    }

    @Operation(summary = "Retry failed payment", description = "Retry a failed payment",
            security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment retry initiated successfully"),
            @ApiResponse(responseCode = "400", description = "Payment cannot be retried"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{paymentId}/retry")
    public ResponseEntity<PaymentResponseDTO> retryPayment(@PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.retryPayment(paymentId));
    }

    @Operation(summary = "Delete payment", description = "Delete a payment from the system",
            security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Payment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all payments", description = "Retrieve all payments with pagination (Admin only)",
            security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied - Admin role required"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/admin/all")
    public ResponseEntity<Page<PaymentResponseDTO>> getAllPayments(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok(paymentService.getAllPayments(pageNo, pageSize));
    }
}