package com.app.notification_application.component;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.app.notification_application.model.Notification;
import com.app.notification_application.model.NotificationType;
import com.app.notification_application.model.User;

@Component
public class NotificationTemplateEngine {

    public Notification createNotification(User user, LocalDate targetDate, NotificationType type) {
        // Uniform daily trigger time set to 09:00 AM
        var scheduledTime = targetDate.atTime(9, 0);
        String subject;
        String body;

        switch (type) {
            case PURCHASE_CONFIRMATION -> {
                subject = "Plan Activated successfully!";
                body = String.format("Hi %s, your plan is now active.", user.getName());
            }
            case RENEWAL_REMINDER_30_DAYS -> {
                subject = "Your subscription expires in 30 days";
                body = String.format("Hi %s, your subscription expires soon. Please renew.", user.getName());
            }
            case EXPIRATION_ALERT -> {
                subject = "Your subscription has expired";
                body = String.format("Hi %s, your subscription has expired today.", user.getName());
            }
            default -> throw new IllegalArgumentException("Unknown notification type: " + type);
        }

        return new Notification(user, scheduledTime, type, subject, body);
    }
}
