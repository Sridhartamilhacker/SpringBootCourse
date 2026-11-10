package com.mastery.ioc.service;

public interface PaymentGateway {
    boolean charge(String orderId, double amount);
}
