package com.order.ordermanagement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "http://localhost:9292")
public interface ProductClient {

    @GetMapping("/products/{id}")
    String getProductById(@PathVariable long id);
    
    
}
