package com.mastery.ioc.service;

import org.springframework.stereotype.Component;

@Component
public class PayPalPaymentGateway implements PaymentGateway {
    @Override
    public boolean charge(String orderId, double amount) {
        System.out.println("[PayPalPaymentGateway] Charging $" + amount + " via PayPal for " + orderId);
        return true;
    }
}
