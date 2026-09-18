package com.opportuna.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Opportunity implements Serializable {
    private static final long serialVersionUID = 1L;

    private final long id;
    private final String title;
    private final String organization;
    private final String type;
    private final LocalDate deadline;
    private final int durationWeeks;
    private final int stipend;
    private final ArrayList<String> requiredSkills;

    public Opportunity(long id, String title, String organization, String type,
                       LocalDate deadline, int durationWeeks, int stipend,
                       List<String> requiredSkills) {
        this.id = id;
        this.title = title;
        this.organization = organization;
        this.type = type;
        this.deadline = deadline;
        this.durationWeeks = durationWeeks;
        this.stipend = stipend;
        this.requiredSkills = new ArrayList<>(requiredSkills);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOrganization() {
        return organization;
    }

    public String getType() {
        return type;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public int getDurationWeeks() {
        return durationWeeks;
    }

    public int getStipend() {
        return stipend;
    }

    public List<String> getRequiredSkills() {
        return new ArrayList<>(requiredSkills);
    }

    @Override
    public String toString() {
        return String.format(
                "#%d | %s | %s | %s | deadline: %s | duration: %d weeks | stipend: INR %d | skills: %s",
                id, title, organization, type, deadline, durationWeeks, stipend, requiredSkills);
    }
}

