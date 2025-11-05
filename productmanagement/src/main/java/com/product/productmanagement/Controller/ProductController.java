package com.product.productmanagement.Controller;


import com.product.productmanagement.Entity.Product;
import com.product.productmanagement.Model.ApiResponse;
import com.product.productmanagement.Service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new ApiResponse<>(HttpStatus.NO_CONTENT.value(),
                            "No products available", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                "Products retrieved successfully", products));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),
                "Product retrieved successfully", product));
    }

    
}
