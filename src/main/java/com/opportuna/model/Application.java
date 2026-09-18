package com.opportuna.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Stack;

public class Application implements Serializable {
    private static final long serialVersionUID = 1L;

    private final long id;
    private final long opportunityId;
    private final LocalDate appliedOn;
    private ApplicationStatus status;
    private String note;
    private final Stack<ApplicationStatus> statusHistory = new Stack<>();

    public Application(long id, long opportunityId, LocalDate appliedOn,
                       ApplicationStatus status, String note) {
        this.id = id;
        this.opportunityId = opportunityId;
        this.appliedOn = appliedOn;
        this.status = status;
        this.note = note == null ? "" : note;
        this.statusHistory.push(status);
    }

    public long getId() {
        return id;
    }

    public long getOpportunityId() {
        return opportunityId;
    }

    public LocalDate getAppliedOn() {
        return appliedOn;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public Stack<ApplicationStatus> getStatusHistory() {
        Stack<ApplicationStatus> copy = new Stack<>();
        copy.addAll(statusHistory);
        return copy;
    }

    public void setStatus(ApplicationStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null.");
        }
        this.status = status;
        this.statusHistory.push(status);
    }

    public void setNote(String note) {
        this.note = note == null ? "" : note;
    }

    @Override
    public String toString() {
        return String.format(
                "Application #%d | opportunity #%d | applied: %s | status: %s | note: %s | history: %s",
                id, opportunityId, appliedOn, status,
                note.isBlank() ? "-" : note, statusHistory);
    }
}

