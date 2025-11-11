package com.order.ordermanagement.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiscountService {

    @Value("${discount.slabs}")
    private String discountSlabs;

    public List<Map<String, Integer>> getDiscountSlabs() {
        List<Map<String, Integer>> slabsList = new ArrayList<>();

        if (discountSlabs != null && !discountSlabs.isEmpty()) {
            String[] slabsArray = discountSlabs.split(",");
            for (String slab : slabsArray) {
                String[] parts = slab.split(":");
                if (parts.length == 2) {
                    try {
                        int amount = Integer.parseInt(parts[0].trim());
                        int percentage = Integer.parseInt(parts[1].trim());
                        Map<String, Integer> slabMap = new HashMap<>();
                        slabMap.put("amount", amount);
                        slabMap.put("discountPercentage", percentage);
                        slabsList.add(slabMap);
                    } catch (NumberFormatException e) {
                        // Log error or handle invalid format
                    }
                }
            }
        }
        return slabsList;
    }

    public double applyOrderDiscount(double orderTotal) {
        System.out.println("Applying discount for order total: " + orderTotal);
        List<Map<String, Integer>> slabs = getDiscountSlabs();
        var discountedTotal = orderTotal;
        for (Map<String, Integer> slab : slabs) {
            Integer amountObj = slab.get("amount");
            Integer discountPercentageObj = slab.get("discountPercentage");
            if (amountObj == null || discountPercentageObj == null) {
                continue;
            }
            int amount = amountObj;
            int discountPercentage = discountPercentageObj;
            if (orderTotal >= amount) {
                double discount = (orderTotal * discountPercentage) / 100.0;
                discountedTotal = Math.min(orderTotal - discount, discountedTotal);
            }
        }
        System.out.println("Final discounted total: " + discountedTotal);
        return discountedTotal;
    }
}