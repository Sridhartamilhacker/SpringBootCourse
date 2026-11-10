package com.mastery.ioc;

import com.mastery.ioc.config.AppConfig;
import com.mastery.ioc.model.Order;
import com.mastery.ioc.service.ContainerInspectorService;
import com.mastery.ioc.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    @DisplayName("ApplicationContext can be inspected and validates singleton scope")
    void testApplicationContextInspection() {
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            ContainerInspectorService inspector = context.getBean(ContainerInspectorService.class);

            // 1. Inspect total beans registered
            assertTrue(inspector.getTotalBeanCount() > 5, "Context should contain Spring internal beans + our beans");

            // 2. Inspect user-defined application beans
            List<String> userBeans = inspector.getUserDefinedBeans();
            System.out.println("Discovered User Beans: " + userBeans);
            assertTrue(userBeans.contains("orderService"));
            assertTrue(userBeans.contains("containerInspectorService"));

            // 3. Verify Default Scope: Singleton (Same reference in heap memory)
            OrderService ref1 = context.getBean(OrderService.class);
            OrderService ref2 = context.getBean(OrderService.class);
            assertSame(ref1, ref2, "Spring default beans MUST be the exact same object reference (Singleton)");
            assertTrue(inspector.isSingleton("orderService"));

            // 4. Verify functionality
            Order order = new Order("ORD-101", 150.0, "vip@example.com");
            assertTrue(ref1.placeOrder(order));
        }
    }
}
