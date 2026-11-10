package com.mastery.ioc;

import com.mastery.ioc.config.AppConfig;
import com.mastery.ioc.model.Order;
import com.mastery.ioc.repository.OrderRepository;
import com.mastery.ioc.service.NotificationService;
import com.mastery.ioc.service.OrderService;
import com.mastery.ioc.service.PaymentGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    @DisplayName("Spring IoC container discovers, wires, and manages all components")
    void testSpringContextWiring() {
        // 1. Boot up Spring's IoC container pointing to AppConfig
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // 2. Ask the container for the assembled OrderService bean
            OrderService orderService = context.getBean(OrderService.class);
            assertNotNull(orderService, "OrderService should be created and wired by Spring IoC");

            // 3. Execute business logic through the container-managed bean
            Order order = new Order("ORD-999", 250.00, "customer@example.com");
            boolean success = orderService.placeOrder(order);

            assertTrue(success, "Order should be placed successfully");
        }
    }

    @Test
    @DisplayName("IoC allows instant unit testing without Spring, using fakes")
    void testPureUnitTestingWithFakes() {
        // Because OrderService uses Constructor Injection, we can pass test fakes directly!
        PaymentGateway fakeGateway = (id, amount) -> true;
        NotificationService fakeNotifier = (recipient, msg) -> {};
        OrderRepository fakeRepo = new OrderRepository() {
            private Order saved;
            @Override public void save(Order order) { this.saved = order; }
            @Override public Optional<Order> findById(String id) { return Optional.ofNullable(saved); }
        };

        OrderService isolatedService = new OrderService(fakeGateway, fakeNotifier, fakeRepo);
        Order testOrder = new Order("ORD-001", 100.0, "test@test.com");

        boolean result = isolatedService.placeOrder(testOrder);
        assertTrue(result);
        assertEquals(Optional.of(testOrder), fakeRepo.findById("ORD-001"));
    }
}
