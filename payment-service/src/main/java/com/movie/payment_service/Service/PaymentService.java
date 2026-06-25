package com.movie.payment_service.Service;

import com.movie.payment_service.DTO.PaymentResponseDTO;
import com.movie.payment_service.Helper.PaymentStatus;
import com.movie.payment_service.RequestDTO.PaymentRequestDTO;
import org.springframework.data.domain.Page;

public interface PaymentService {

    PaymentResponseDTO processPayment(PaymentRequestDTO requestDTO);

    PaymentResponseDTO getPaymentById(Long paymentId);

    PaymentResponseDTO getPaymentByBookingId(Long bookingId);

    Page<PaymentResponseDTO> getPaymentsByUserId(Long userId, int pageNo, int pageSize);

    Page<PaymentResponseDTO> getPaymentsByStatus(PaymentStatus status, int pageNo, int pageSize);

    PaymentResponseDTO updatePaymentStatus(Long paymentId, PaymentStatus status);

    PaymentResponseDTO refundPayment(Long paymentId);

    PaymentResponseDTO retryPayment(Long paymentId);

    void deletePayment(Long paymentId);

    Page<PaymentResponseDTO> getAllPayments(int pageNo, int pageSize);
}