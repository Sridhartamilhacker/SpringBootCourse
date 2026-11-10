package com.mastery.ioc.service;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        System.out.println("[EmailNotificationService] Sending to " + recipient + ": " + message);
    }
}
