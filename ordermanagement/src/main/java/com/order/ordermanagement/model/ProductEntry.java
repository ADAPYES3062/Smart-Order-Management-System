package com.order.ordermanagement.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProductEntry {
    private Long productId;
    private Integer quantity;   
}
