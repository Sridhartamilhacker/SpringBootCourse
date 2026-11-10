package com.mastery.audit;

import org.springframework.stereotype.Service;

@Service
public class AuditService {
    public void record(String action) {
        System.out.println("[AuditService] " + action);
    }
}
