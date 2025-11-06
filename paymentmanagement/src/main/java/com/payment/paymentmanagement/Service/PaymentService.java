package com.payment.paymentmanagement.Service;

import com.payment.paymentmanagement.Entity.Payment;
import com.payment.paymentmanagement.Exception.PaymentNotFoundException;
import com.payment.paymentmanagement.Model.PaymentRequest;
import com.payment.paymentmanagement.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

      public List<Payment> getAllPayments() {
    return paymentRepository.findAll();
}

    public Payment processPayment(PaymentRequest request) {
        Payment payment = new Payment(
                request.getOrderId(),
                request.getPaymentMode(),
                request.getAmount(),
                (Math.random() > 0.8) ? "SUCCESS" : "FAILED"
        );
        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with ID " + id + " not found"));
    }


  

}