package com.opportuna.repository.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jpa_opportunities")
public class JpaOpportunity {
    @Id
    private long id;

    private String title;
    private String organization;
    private String type;
    private String deadline;
    private int durationWeeks;
    private int stipend;

    @Column(length = 1000)
    private String requiredSkills;

    protected JpaOpportunity() {
    }

    public JpaOpportunity(long id, String title, String organization, String type,
                          String deadline, int durationWeeks, int stipend,
                          String requiredSkills) {
        this.id = id;
        this.title = title;
        this.organization = organization;
        this.type = type;
        this.deadline = deadline;
        this.durationWeeks = durationWeeks;
        this.stipend = stipend;
        this.requiredSkills = requiredSkills;
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

    public String getDeadline() {
        return deadline;
    }

    public int getDurationWeeks() {
        return durationWeeks;
    }

    public int getStipend() {
        return stipend;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }
}

