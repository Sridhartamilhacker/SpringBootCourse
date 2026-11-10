package com.mastery.ioc.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Pure utility component — no business logic, no DB access.
 * @Component is correct here: it doesn't belong to service or persistence layers.
 */
@Component
public class OrderIdGenerator {

    /**
     * Generates a unique, prefixed order ID.
     * e.g. "ORD-3f2a1b" — avoids raw UUIDs leaking into client-facing APIs.
     */
    public String generate() {
        String shortId = UUID.randomUUID().toString().substring(0, 6);
        return "ORD-" + shortId;
    }
}
