package com.app.notification_application.model;

import java.time.LocalDate;

public class PlanInfo {
    private LocalDate startDate;
    private LocalDate expirationDate; // Changed from int durationInDays

    public PlanInfo() {
    }

    public PlanInfo(LocalDate startDate, LocalDate expirationDate) {
        this.startDate = startDate;
        this.expirationDate = expirationDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
