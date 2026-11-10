package com.mastery.ioc.service;

import org.springframework.stereotype.Service;

/**
 * FIXED: PricingService only calculates prices and discounts.
 * It has zero dependencies on OrderService, breaking the circular dependency cycle!
 */
@Service
public class PricingService {

    public double applyDiscount(double amount) {
        return amount * 0.9;
    }
}
