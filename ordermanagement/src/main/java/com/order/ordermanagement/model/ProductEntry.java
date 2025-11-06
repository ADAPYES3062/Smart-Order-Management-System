package com.order.ordermanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data                 // generates getters, setters, equals, hashCode, toString
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntry {

    @Column(name = "product_id")   // optional – only needed if you want a different column name
    private Long productId;

    @Column(name = "quantity")
    private Integer quantity;
}
