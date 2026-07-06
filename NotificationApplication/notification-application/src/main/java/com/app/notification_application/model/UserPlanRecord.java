package com.app.notification_application.model;

public class UserPlanRecord {
    private User user;
    private PlanInfo planInfo;

    // Default constructor for Jackson
    public UserPlanRecord() {
    }

    public UserPlanRecord(User user, PlanInfo planInfo) {
        this.user = user;
        this.planInfo = planInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PlanInfo getPlanInfo() {
        return planInfo;
    }

    public void setPlanInfo(PlanInfo planInfo) {
        this.planInfo = planInfo;
    }
}