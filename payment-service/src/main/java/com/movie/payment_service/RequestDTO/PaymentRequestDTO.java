package com.movie.payment_service.RequestDTO;

import com.movie.payment_service.Helper.PaymentMethod;
import com.movie.payment_service.Helper.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request object for creating/processing a payment")
public class PaymentRequestDTO {

    @NotNull(message = "Booking ID is required")
    @Min(value = 1, message = "Booking ID must be at least 1")
    @Schema(description = "ID of the booking this payment belongs to", example = "123", required = true, minimum = "1")
    private Long bookingId;

    @NotNull(message = "User ID is required")
    @Min(value = 1, message = "User ID must be at least 1")
    @Schema(description = "ID of the user making the payment", example = "456", required = true, minimum = "1")
    private Long userId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @DecimalMax(value = "999999.99", message = "Amount cannot exceed 999999.99")
    @Schema(description = "Payment amount", example = "250.00", required = true, minimum = "0.01", maximum = "999999.99")
    private Double amount;

    @NotNull(message = "Payment method is required")
    @Schema(description = "Payment method", required = true,
            example = "CREDIT_CARD", allowableValues = {"CREDIT_CARD", "DEBIT_CARD", "UPI", "NET_BANKING", "WALLET", "CASH"})
    private PaymentMethod paymentMethod;

    @Schema(description = "Payment gateway transaction ID", example = "TXN_123456789", maxLength = 100)
    private String transactionId;

    @Schema(description = "Payment gateway name", example = "Razorpay", maxLength = 50)
    private String paymentGateway;

    @Schema(description = "Additional payment metadata", example = "{\"cardLast4\": \"1234\"}", maxLength = 500)
    private String metadata;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Schema(description = "Payment description", example = "Payment for movie tickets booking #123", maxLength = 500)
    private String description;
}