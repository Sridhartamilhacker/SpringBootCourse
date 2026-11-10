package com.mastery.ioc.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class StripePaymentGateway implements PaymentGateway {
    @Override
    public boolean charge(String orderId, double amount) {
        System.out.println("[StripePaymentGateway] Charging $" + amount + " for order " + orderId);
        return amount > 0;
    }
}
