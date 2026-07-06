package com.app.notification_application.service;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.app.notification_application.model.Notification;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailDispatcherService {

    private final JavaMailSender mailSender;

    // Automatic structural constructor injection
    public EmailDispatcherService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Iterates through a sequenced collection of alerts and triggers outbound mail
     * dispatch
     */
    public void processAndSendQueue(List<Notification> notificationQueue) {
        log.info("Initializing active processing for {} queued notifications...", notificationQueue.size());

        for (Notification targetAlert : notificationQueue) {
            try {
                sendActualEmail(targetAlert);
            } catch (Exception err) {
                log.error("Failed to transmit notification to user ID {}: {}",
                        targetAlert.getUser().getId(), err.getMessage());
            }
        }

        log.info("Outbound notification transmission sequence finished.");
    }

    private void sendActualEmail(Notification notification) {
        SimpleMailMessage standardMail = new SimpleMailMessage();

        standardMail.setFrom("no-reply@yourcompany.com");
        standardMail.setTo(notification.getUser().getEmail());
        standardMail.setSubject(notification.getEmailSubject());
        standardMail.setText(notification.getEmailBody());

        // Standard execution call over the active network socket connection
        mailSender.send(standardMail);

        log.info("Successfully sent email to {} [Trigger Date: {}]",
                notification.getUser().getEmail(), notification.getScheduledTime().toLocalDate());
    }
}