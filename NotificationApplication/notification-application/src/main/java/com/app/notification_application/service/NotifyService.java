package com.app.notification_application.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.notification_application.component.NotificationTemplateEngine;
import com.app.notification_application.model.Notification;
import com.app.notification_application.model.NotificationType;
import com.app.notification_application.model.PlanInfo;
import com.app.notification_application.model.User;
import com.app.notification_application.model.UserPlanRecord;

@Service
public class NotifyService {

    private final NotificationTemplateEngine templateEngine;

    public NotifyService(NotificationTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public List<Notification> generateScheduledNotifications(List<UserPlanRecord> records) {
        List<Notification> masterQueue = new ArrayList<>();

        for (UserPlanRecord record : records) {
            User user = record.getUser();
            PlanInfo plan = record.getPlanInfo();

            LocalDate purchaseDate = plan.getStartDate();
            LocalDate expirationDate = plan.getExpirationDate(); // Directly fetched from the JSON parameter
            LocalDate reminderDate = expirationDate.minusDays(30);

            // 1. First Notification: Purchase confirmation date
            masterQueue
                    .add(templateEngine.createNotification(user, purchaseDate, NotificationType.PURCHASE_CONFIRMATION));

            // 2. Second Notification: 30 days before expiration date
            // Safe fallback check: Ensure the subscription lasts long enough to have a
            // 30-day early alert window
            if (!reminderDate.isBefore(purchaseDate)) {
                masterQueue.add(templateEngine.createNotification(user, reminderDate,
                        NotificationType.RENEWAL_REMINDER_30_DAYS));
            }

            // 3. Third Notification: Day of expiration date
            masterQueue.add(templateEngine.createNotification(user, expirationDate, NotificationType.EXPIRATION_ALERT));
        }

        // Return a single unified collection sorted chronologically in ascending order
        return masterQueue.stream()
                .sorted(Comparator.comparing(Notification::getScheduledTime))
                .toList();
    }
}
