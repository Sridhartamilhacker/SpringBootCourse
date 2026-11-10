package com.mastery.ioc.service;

import com.mastery.ioc.model.Order;
import com.mastery.ioc.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final PaymentGateway paymentGateway;
    private final NotificationService notificationService;
    private final OrderRepository orderRepository;

    public OrderService(PaymentGateway paymentGateway,
                        NotificationService notificationService,
                        OrderRepository orderRepository) {
        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
        this.orderRepository = orderRepository;
    }

    public boolean placeOrder(Order order) {
        boolean charged = paymentGateway.charge(order.id(), order.amount());
        if (!charged) {
            return false;
        }

        orderRepository.save(order);
        notificationService.sendNotification(order.customerEmail(), "Order " + order.id() + " confirmed!");
        return true;
    }
}
