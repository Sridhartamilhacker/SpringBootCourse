package com.mastery.ioc.repository;

import com.mastery.ioc.model.Order;
import java.util.Optional;

public interface OrderRepository {
    void save(Order order);
    Optional<Order> findById(String id);
}
