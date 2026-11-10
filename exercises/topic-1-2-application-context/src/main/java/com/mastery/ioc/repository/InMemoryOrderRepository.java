package com.mastery.ioc.repository;

import com.mastery.ioc.model.Order;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> store = new ConcurrentHashMap<>();

    @Override
    public void save(Order order) {
        store.put(order.id(), order);
        System.out.println("[InMemoryOrderRepository] Saved order: " + order.id());
    }

    @Override
    public Optional<Order> findById(String id) {

        return Optional.ofNullable(store.get(id));
    }
}
