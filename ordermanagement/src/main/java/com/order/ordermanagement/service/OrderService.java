package com.order.ordermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.order.ordermanagement.client.PaymentClient;
import com.order.ordermanagement.client.ProductClient;
import com.order.ordermanagement.entity.Order;
import com.order.ordermanagement.model.OrderDto;
import com.order.ordermanagement.model.PaymentRequest;
import com.order.ordermanagement.model.PaymentResponse;
import com.order.ordermanagement.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

    // @Autowired
    // ProductClient productClient;

    @Autowired
    PaymentClient paymentClient;

    @Autowired
    OrderRepository orderRepository;

    public OrderDto getOrderDetails(Long id) {
        // TODO Auto-generated method stub
        Order order = orderRepository.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));
        System.out.println("Order fetched: " + order.toString());
        return convertEntityToDto(order);
    }

    public OrderDto convertEntityToDto(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setProducts(order.getProducts());
        orderDto.setStatus(order.getStatus());
        orderDto.setTotalAmount(order.getTotalAmount());
        orderDto.setTransactionId(order.getTransactionId());
        return orderDto;    
    }  
    
    public Order convertDtoToEntity(OrderDto orderDto){
        Order order = new Order();
        order.setProducts(orderDto.getProducts());
        order.setStatus(orderDto.getStatus());
        order.setTotalAmount(orderDto.getTotalAmount());
        return order;    
    }

    public OrderDto createOrder(OrderDto orderDto) {
        Order order = convertDtoToEntity(orderDto);
        order.setStatus("WAITING FOR PAYMENT");
        order.setProducts(orderDto.getProducts());
        Order savedOrder = orderRepository.save(order);


        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(savedOrder.getId());
        paymentRequest.setAmount(savedOrder.getTotalAmount());
        paymentRequest.setPaymentMode("ONLINE");


        // Call Payment Service to process payment
        PaymentResponse paymentResponse = paymentClient.processPayment(paymentRequest.getData());
        System.out.println("Payment Response: " + paymentResponse.getStatus());

        savedOrder.setTransactionId(paymentResponse.getId());
        System.out.println("Transaction ID set in order: " + paymentResponse.getId());
        savedOrder.setStatus("COMPLETED");
        Order updatedOrder = orderRepository.save(savedOrder);

        return convertEntityToDto(updatedOrder);
    }
    
}
