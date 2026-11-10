package com.mastery.ioc;

import com.mastery.ioc.config.AppConfig;
import com.mastery.ioc.service.OrderService;
import com.mastery.ioc.util.OrderIdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    @Test
    @DisplayName("@Component utility is wired and generates a prefixed ID")
    void testOrderIdGeneratorIsABean() {
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // @Component — Spring registered this utility automatically
            OrderIdGenerator generator = context.getBean(OrderIdGenerator.class);
            assertNotNull(generator);

            String id = generator.generate();
            System.out.println("Generated Order ID: " + id);
            assertTrue(id.startsWith("ORD-"), "ID must follow the ORD- convention");
            assertEquals(10, id.length(), "ORD- (4) + 6-char UUID segment = 10 chars");
        }
    }

    @Test
    @DisplayName("@Service places a valid order end-to-end using all stereotypes")
    void testPlaceOrderSuccess() {
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            OrderService orderService = context.getBean(OrderService.class);

            String orderId = orderService.placeOrder(199.99, "buyer@shop.com");

            assertNotNull(orderId);
            assertTrue(orderId.startsWith("ORD-"), "Order ID must carry the ORD- prefix");
            System.out.println("Order placed successfully: " + orderId);
        }
    }

    @Test
    @DisplayName("@Repository exception translation: null order throws DataIntegrityViolationException")
    void testRepositoryExceptionTranslation() {
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // Directly access the repository to trigger the null constraint check
            var repository = context.getBean(com.mastery.ioc.repository.OrderRepository.class);

            // Saving null triggers a DataIntegrityViolationException (Spring's translated exception)
            // NOT a raw vendor-specific SQLException — this is @Repository's job!
            assertThrows(DataIntegrityViolationException.class, () -> repository.save(null),
                    "Null order should throw DataIntegrityViolationException (Spring's unified hierarchy)");
        }
    }
}
