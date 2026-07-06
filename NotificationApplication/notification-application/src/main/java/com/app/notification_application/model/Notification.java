package com.app.notification_application.model;

import java.time.LocalDateTime;

public class Notification {
    private final User user;
    private final LocalDateTime scheduledTime;
    private final NotificationType type;
    private final String emailSubject;
    private final String emailBody;

    public Notification(User user2, LocalDateTime scheduledTime, NotificationType type, String emailSubject,
            String emailBody) {
        this.user = user2;
        this.scheduledTime = scheduledTime;
        this.type = type;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public NotificationType getType() {
        return type;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    @Override
    public String toString() {
        return String.format("[%s] -> Email to: %s | Subject: %s", scheduledTime, user.getEmail(), emailSubject);
    }
}
