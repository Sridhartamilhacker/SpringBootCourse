package com.mastery.ioc.repository;

import com.mastery.ioc.model.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Repository signals TWO things to Spring:
 *   1. Register this as a bean (via @Component underneath).
 *   2. Wrap this class in a proxy that translates raw DB exceptions
 *      (SQLException, HibernateException) into Spring's DataAccessException hierarchy.
 *
 * Your @Service layer will only ever see DataAccessException — never vendor-specific exceptions.
 */
@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<String, Order> store = new ConcurrentHashMap<>();

    @Override
    public void save(Order order) {
        if (order == null || order.id() == null) {
            // Simulates what a DB would throw for a null constraint violation.
            // Spring's proxy translates this into DataIntegrityViolationException.
            throw new DataIntegrityViolationException(
                    "Cannot save order: id must not be null (simulated DB constraint)");
        }
        store.put(order.id(), order);
        System.out.println("[InMemoryOrderRepository] Saved order: " + order.id());
    }

    @Override
    public Optional<Order> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}
