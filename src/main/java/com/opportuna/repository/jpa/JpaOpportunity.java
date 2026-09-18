package com.opportuna.repository.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * JPA entity representing an OPPORTUNA opportunity.
 */
@Entity
@Table(name = "opportunities")
public class JpaOpportunity {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "organization", nullable = false, length = 150)
    private String organization;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "duration_weeks", nullable = false)
    private int durationWeeks;

    @Column(name = "stipend", nullable = false)
    private int stipend;

    @Column(name = "required_skills", nullable = false, length = 1000)
    private String requiredSkills;

    /**
     * Required by JPA.
     */
    protected JpaOpportunity() {
    }

    public JpaOpportunity(long id,
                          String title,
                          String organization,
                          String type,
                          String deadline,
                          int durationWeeks,
                          int stipend,
                          String requiredSkills) {
        this(
                id,
                title,
                organization,
                type,
                LocalDate.parse(deadline),
                durationWeeks,
                stipend,
                requiredSkills
        );
    }

    public JpaOpportunity(long id,
                          String title,
                          String organization,
                          String type,
                          LocalDate deadline,
                          int durationWeeks,
                          int stipend,
                          String requiredSkills) {
        this.id = id;
        this.title = title;
        this.organization = organization;
        this.type = type;
        this.deadline = deadline;
        this.durationWeeks = durationWeeks;
        this.stipend = stipend;
        this.requiredSkills = requiredSkills == null ? "" : requiredSkills;
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

    public String getRequiredSkills() {
        return requiredSkills;
    }

    @Override
    public String toString() {
        return String.format(
                "#%d | %s | %s | %s | deadline: %s | duration: %d weeks | stipend: INR %d | skills: %s",
                id,
                title,
                organization,
                type,
                deadline,
                durationWeeks,
                stipend,
                requiredSkills
        );
    }
}
