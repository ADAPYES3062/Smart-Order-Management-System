package com.order.ordermanagement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.order.ordermanagement.model.ApiResponse;
import com.order.ordermanagement.model.PaymentRequest;
import com.order.ordermanagement.model.PaymentResponse;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "payment-service", url = "http://localhost:9191")
public interface PaymentClient {

    @GetMapping("/payments/{id}")
    String getPaymentById(@PathVariable long id);

    @PostMapping(value = "/payments", consumes = "application/json", produces = "application/json")
    ApiResponse<PaymentResponse> processPayment(@RequestBody ApiResponse<PaymentRequest> paymentRequest);

    
}