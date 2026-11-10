package com.mastery.ioc.service;

import com.mastery.audit.AuditService;
import com.mastery.ioc.model.Order;
import com.mastery.ioc.repository.OrderRepository;
import com.mastery.ioc.util.OrderIdGenerator;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * @Service signals: this class contains BUSINESS LOGIC.
 * It orchestrates the other layers but never talks to the DB directly.
 *
 * Constructor injection rules applied:
 *   - All dependencies are 'private final' (immutable after construction).
 *   - No @Autowired annotation needed — Spring auto-detects the single constructor.
 *   - Every dependency is an interface, not a concrete class.
 */
@Service
public class OrderService {

    private final PaymentGateway paymentGateway;
    private final NotificationService notificationService;
    private final OrderRepository orderRepository;
    private final AuditService auditService;
    private final OrderIdGenerator idGenerator;
    private final PricingService pricingService;  // <-- NEW: circular dependency injected

    public OrderService(PaymentGateway paymentGateway,
                        NotificationService notificationService,
                        OrderRepository orderRepository,
                        AuditService auditService,
                        OrderIdGenerator idGenerator,
                        PricingService pricingService) {  // <-- NEW
        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
        this.orderRepository = orderRepository;
        this.auditService = auditService;
        this.idGenerator = idGenerator;
        this.pricingService = pricingService;
    }

    /**
     * Places an order.
     * Uses the @Component utility (idGenerator) to create a unique ID,
     * and handles DataAccessException from the @Repository layer.
     *
     * @return the generated order ID on success
     * @throws DataAccessException if persistence fails (already translated by @Repository proxy)
     */
    public String placeOrder(double amount, String customerEmail) {
        String orderId = idGenerator.generate();
        double finalAmount = pricingService.applyDiscount(amount); // Apply discount
        Order order = new Order(orderId, finalAmount, customerEmail);

        boolean charged = paymentGateway.charge(orderId, finalAmount);
        if (!charged) {
            throw new IllegalStateException("Payment failed for order: " + orderId);
        }

        orderRepository.save(order);
        auditService.record("Placed order " + orderId + " with discounted total $" + finalAmount);
        notificationService.sendNotification(customerEmail, "Order " + orderId + " confirmed!");

        return orderId;
    }
}
