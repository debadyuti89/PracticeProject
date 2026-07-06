package com.app.notification_application;

import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;

import com.app.notification_application.model.Notification;
import com.app.notification_application.model.UserPlanRecord;
import com.app.notification_application.service.EmailDispatcherService;
import com.app.notification_application.service.NotifyService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class NotificationApplication implements CommandLineRunner {

	private final NotifyService notifyService;
	private final ObjectMapper objectMapper;
	private final ResourceLoader resourceLoader;
	private final EmailDispatcherService emailDispatcherService;

	// Spring Boot automatically configures and injects ObjectMapper and
	// ResourceLoader
	public NotificationApplication(NotifyService notifyService, ObjectMapper objectMapper,
			ResourceLoader resourceLoader, EmailDispatcherService emailDispatcherService) {
		this.notifyService = notifyService;
		this.objectMapper = objectMapper;
		this.resourceLoader = resourceLoader;
		this.emailDispatcherService = emailDispatcherService;
	}

	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// 1. Locate the file in the classpath resources bundle
		var resource = resourceLoader.getResource("classpath:users-plans.json");

		System.out.println("Reading seed parameters from JSON file...");

		// 2. Open an input stream and safely parse the JSON array into domain objects
		try (InputStream inputStream = resource.getInputStream()) {
			List<UserPlanRecord> inputs = objectMapper.readValue(
					inputStream,
					new TypeReference<List<UserPlanRecord>>() {
					});

			// 3. Process records through your custom chronological notification engine
			List<Notification> targetSchedule = notifyService.generateScheduledNotifications(inputs);
			emailDispatcherService.processAndSendQueue(targetSchedule);
			System.out.println("\n--- CHRONOLOGICAL DISPATCH SCHEDULE FROM JSON ---");
			targetSchedule.forEach(System.out::println);
			System.out.println("-------------------------------------------------\n");

		} catch (Exception e) {
			System.err.println("Failed to read or parse the JSON file: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
