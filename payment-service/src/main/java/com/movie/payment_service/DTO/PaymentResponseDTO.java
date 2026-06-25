package com.movie.payment_service.DTO;

import com.movie.payment_service.Helper.PaymentMethod;
import com.movie.payment_service.Helper.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Response object for payment details")
public class PaymentResponseDTO {

    @Schema(description = "Payment ID", example = "1")
    private Long id;

    @Schema(description = "Booking ID", example = "123")
    private Long bookingId;

    @Schema(description = "User ID", example = "456")
    private Long userId;

    @Schema(description = "Payment amount", example = "250.00")
    private Double amount;

    @Schema(description = "Current payment status", example = "COMPLETED")
    private PaymentStatus status;

    @Schema(description = "Payment method used", example = "CREDIT_CARD")
    private PaymentMethod paymentMethod;

    @Schema(description = "Payment gateway transaction ID", example = "TXN_123456789")
    private String transactionId;

    @Schema(description = "Payment gateway name", example = "Razorpay")
    private String paymentGateway;

    @Schema(description = "Payment description", example = "Payment for movie tickets booking #123")
    private String description;

    @Schema(description = "Payment metadata", example = "{\"cardLast4\": \"1234\"}")
    private String metadata;

    @Schema(description = "Payment creation timestamp", example = "2024-01-15T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Payment last update timestamp", example = "2024-01-15T10:30:05")
    private LocalDateTime updatedAt;

    @Schema(description = "Payment completion timestamp", example = "2024-01-15T10:30:05")
    private LocalDateTime completedAt;

    @Schema(description = "Payment failure reason if any", example = "Insufficient funds")
    private String failureReason;

    @Schema(description = "Number of retry attempts", example = "0")
    private Integer retryCount;
}