package com.opportuna.service;

import com.opportuna.exception.InvalidInputException;
import com.opportuna.model.Application;
import com.opportuna.model.ApplicationStatus;
import com.opportuna.model.Opportunity;
import com.opportuna.model.StudentProfile;
import com.opportuna.repository.ApplicationRepository;
import com.opportuna.repository.OpportunityRepository;
import com.opportuna.util.ActivityLog;
import com.opportuna.util.InputValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicLong;

public class OpportunityService {
    private final OpportunityRepository opportunities;
    private final ApplicationRepository applications;
    private final MatchEngine matchEngine;
    private final PriorityEngine priorityEngine;
    private final AtomicLong nextApplicationId;

    public OpportunityService(OpportunityRepository opportunities,
                              ApplicationRepository applications,
                              MatchEngine matchEngine,
                              PriorityEngine priorityEngine) {
        this.opportunities = opportunities;
        this.applications = applications;
        this.matchEngine = matchEngine;
        this.priorityEngine = priorityEngine;

        long maxId = applications.findAll().stream()
                .mapToLong(Application::getId)
                .max()
                .orElse(0L);
        this.nextApplicationId = new AtomicLong(maxId + 1);
    }

    public List<Opportunity> all() {
        return opportunities.findAll().stream()
                .sorted(Comparator.comparing(Opportunity::getDeadline)
                        .thenComparing(Opportunity::getTitle))
                .toList();
    }

    public void addOpportunity(String title,
                                String organization,
                                String type,
                                LocalDate deadline,
                                int durationWeeks,
                                int stipend,
                                List<String> skills) throws InvalidInputException {
        String cleanTitle = InputValidator.text(title, "Title");
        String cleanOrganization = InputValidator.text(organization, "Organization");
        String cleanType = InputValidator.text(type, "Type");

        if (deadline == null) {
            throw new InvalidInputException("Deadline is required.");
        }
        if (deadline.isBefore(LocalDate.now())) {
            throw new InvalidInputException("Deadline cannot be in the past.");
        }
        if (durationWeeks < 0 || stipend < 0) {
            throw new InvalidInputException("Duration and stipend cannot be negative.");
        }
        if (skills == null || skills.stream().noneMatch(s -> s != null && !s.isBlank())) {
            throw new InvalidInputException("At least one required skill is needed.");
        }

        long nextId = opportunities.findAll().stream()
                .mapToLong(Opportunity::getId)
                .max()
                .orElse(0L) + 1;

        Opportunity opportunity = new Opportunity(
                nextId,
                cleanTitle,
                cleanOrganization,
                cleanType,
                deadline,
                durationWeeks,
                stipend,
                cleanSkills(skills));

        opportunities.save(opportunity);
        ActivityLog.write("Added opportunity #" + nextId + ": " + cleanTitle);
    }

    public void deleteOpportunity(long opportunityId) throws InvalidInputException {
        Opportunity opportunity = opportunities.findById(opportunityId)
                .orElseThrow(() -> new InvalidInputException(
                        "Opportunity not found: " + opportunityId));

        boolean hasApplications = applications.findAll().stream()
                .anyMatch(application -> application.getOpportunityId() == opportunityId);

        if (hasApplications) {
            throw new InvalidInputException(
                    "Cannot delete an opportunity that already has applications.");
        }

        opportunities.deleteById(opportunityId);
        ActivityLog.write("Deleted opportunity #" + opportunityId);
        System.out.println("Deleted: " + opportunity.getTitle());
    }

    public int matchScore(StudentProfile profile, Opportunity opportunity) {
        return matchEngine.score(profile, opportunity);
    }

    public List<String> matchedSkills(StudentProfile profile, Opportunity opportunity) {
        return matchEngine.matchedSkills(profile, opportunity);
    }

    public List<String> missingSkills(StudentProfile profile, Opportunity opportunity) {
        return matchEngine.missingSkills(profile, opportunity);
    }

    public int priority(StudentProfile profile, Opportunity opportunity) {
        return priorityEngine.priority(opportunity, matchScore(profile, opportunity));
    }

    public List<Opportunity> rankedFor(StudentProfile profile) {
        List<Opportunity> all = all();

        Map<Long, Integer> matchCache = new HashMap<>();
        PriorityQueue<OpportunityScore> queue = new PriorityQueue<>(
                Comparator.comparingInt(OpportunityScore::priority).reversed()
                        .thenComparing(score -> score.opportunity().getDeadline())
                        .thenComparing(score -> score.opportunity().getTitle()));

        for (Opportunity opportunity : all) {
            int match = matchEngine.score(profile, opportunity);
            matchCache.put(opportunity.getId(), match);
            int priority = priorityEngine.priority(
                    opportunity, matchCache.get(opportunity.getId()));
            queue.offer(new OpportunityScore(opportunity, match, priority));
        }

        List<Opportunity> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll().opportunity());
        }
        return result;
    }

    public Application apply(long opportunityId, String note) throws InvalidInputException {
        Opportunity opportunity = opportunities.findById(opportunityId)
                .orElseThrow(() -> new InvalidInputException(
                        "Opportunity not found: " + opportunityId));

        if (opportunity.getDeadline().isBefore(LocalDate.now())) {
            throw new InvalidInputException("This opportunity has already closed.");
        }

        boolean alreadyApplied = applications.findAll().stream()
                .anyMatch(application ->
                        application.getOpportunityId() == opportunityId
                                && application.getStatus() != ApplicationStatus.REJECTED);

        if (alreadyApplied) {
            throw new InvalidInputException(
                    "You already have an active application for this opportunity.");
        }

        Application application = new Application(
                nextApplicationId.getAndIncrement(),
                opportunityId,
                LocalDate.now(),
                ApplicationStatus.APPLIED,
                note == null ? "" : note.trim());

        applications.save(application);
        ActivityLog.write("Applied to opportunity #" + opportunityId);
        return application;
    }

    public List<Application> applications() {
        return applications.findAll().stream()
                .sorted(Comparator.comparing(Application::getAppliedOn)
                        .reversed()
                        .thenComparing(Application::getId))
                .toList();
    }

    public void updateApplication(long id,
                                  ApplicationStatus status,
                                  String note) throws InvalidInputException {
        if (status == null) {
            throw new InvalidInputException("Status is required.");
        }

        Application application = applications.findById(id)
                .orElseThrow(() -> new InvalidInputException(
                        "Application not found: " + id));

        application.setStatus(status);
        if (note != null && !note.isBlank()) {
            application.setNote(note.trim());
        }

        applications.save(application);
        ActivityLog.write("Updated application #" + id + " to " + status);
    }

    public record OpportunityScore(Opportunity opportunity, int match, int priority) {
    }

    private List<String> cleanSkills(List<String> skills) {
        List<String> result = new ArrayList<>();
        for (String skill : skills) {
            if (skill != null && !skill.isBlank()) {
                String clean = skill.trim();
                if (result.stream().noneMatch(existing ->
                        existing.equalsIgnoreCase(clean))) {
                    result.add(clean);
                }
            }
        }
        return result;
    }
}
