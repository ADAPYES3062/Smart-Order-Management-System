package com.payment.paymentmanagement.Model;


public class PaymentResponse {
    private Long id;
    private Long orderId;
    private String paymentMode;
    private Double amount;
    private String status;

    public PaymentResponse(Long id, Long orderId, String paymentMode, Double amount, String status) {
        this.id = id;
        this.orderId = orderId;
        this.paymentMode = paymentMode;
        this.amount = amount;
        this.status = status;
    }

    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public String getPaymentMode() { return paymentMode; }
    public Double getAmount() { return amount; }
    public String getStatus() { return status; }
}