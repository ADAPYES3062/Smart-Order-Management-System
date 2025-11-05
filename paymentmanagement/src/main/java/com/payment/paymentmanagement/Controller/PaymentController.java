package com.payment.paymentmanagement.Controller;

import com.payment.paymentmanagement.Entity.Payment;
import com.payment.paymentmanagement.Exception.PaymentNotFoundException;
import com.payment.paymentmanagement.Model.ApiResponse;
import com.payment.paymentmanagement.Model.PaymentRequest;
import com.payment.paymentmanagement.Model.PaymentResponse;
import com.payment.paymentmanagement.Service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Root Endpoint - sanity check
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<PaymentResponse>>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();

        if (payments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(HttpStatus.NO_CONTENT.value(),
                            "No payments found", null));
        }

        List<PaymentResponse> responseList = payments.stream()
                .map(payment -> new PaymentResponse(
                        payment.getId(),
                        payment.getOrderId(),
                        payment.getPaymentMode(),
                        payment.getAmount(),
                        payment.getStatus()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Payments retrieved successfully", responseList)
        );
}
    /**
     * POST /payments — process a payment
     */
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponse>> processPayment(@RequestBody @Valid PaymentRequest request) {
        Payment payment = paymentService.processPayment(request);

        PaymentResponse response = new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getPaymentMode(),
                payment.getAmount(),
                payment.getStatus()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(),
                        "Payment processed successfully", response));
    }

    /**
     * GET /payments/{id} — fetch payment details
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponse>> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment == null) {
            throw new PaymentNotFoundException("Payment not found with id: " + id);
        }

        PaymentResponse response = new PaymentResponse(
                payment.getId(),
                payment.getOrderId(),
                payment.getPaymentMode(),
                payment.getAmount(),
                payment.getStatus()
        );

        return ResponseEntity.ok(
                new ApiResponse<>(HttpStatus.OK.value(),
                        "Payment retrieved successfully", response)
        );
    }
}
