package com.opportuna.app;

import com.opportuna.exception.InvalidInputException;
import com.opportuna.model.Application;
import com.opportuna.model.ApplicationStatus;
import com.opportuna.model.Opportunity;
import com.opportuna.model.StudentProfile;
import com.opportuna.repository.ApplicationRepository;
import com.opportuna.repository.OpportunityRepository;
import com.opportuna.repository.file.FileApplicationRepository;
import com.opportuna.repository.file.FileOpportunityRepository;
import com.opportuna.service.MatchEngine;
import com.opportuna.service.OpportunityService;
import com.opportuna.service.PriorityEngine;
import com.opportuna.thread.ReminderWorker;
import com.opportuna.util.InputValidator;
import com.opportuna.util.ReflectionCatalog;

import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final OpportunityService service;
    private final StudentProfile profile;

    public Main() {
        OpportunityRepository opportunityRepository =
                new FileOpportunityRepository(Path.of("data", "opportunities.dat"));
        ApplicationRepository applicationRepository =
                new FileApplicationRepository(Path.of("data", "applications.dat"));

        service = new OpportunityService(
                opportunityRepository,
                applicationRepository,
                new MatchEngine(),
                new PriorityEngine());

        profile = createProfile();
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.seedDemoDataIfEmpty();
        app.run();
    }

    private StudentProfile createProfile() {
        System.out.println("========================================");
        System.out.println("          OPPORTUNA - SETUP");
        System.out.println("========================================");

        String name = ask("Your name");
        String course = ask("Course");
        List<String> skills = parseSkills(ask("Skills (comma separated)"));

        if (skills.isEmpty()) {
            skills = new ArrayList<>(List.of("Java", "Python", "SQL"));
            System.out.println("No skills entered. Demo skills will be used for this session.");
        }

        return new StudentProfile(name.trim(), course.trim(), skills);
    }

    private void seedDemoDataIfEmpty() {
        if (!service.all().isEmpty()) {
            return;
        }

        try {
            LocalDate today = LocalDate.now();

            service.addOpportunity(
                    "Java Backend Intern",
                    "TechWorks",
                    "Internship",
                    today.plusDays(5),
                    12,
                    15000,
                    List.of("Java", "OOP", "SQL", "Git"));

            service.addOpportunity(
                    "Data Analyst Trainee",
                    "Insight Labs",
                    "Internship",
                    today.plusDays(10),
                    8,
                    12000,
                    List.of("Python", "SQL", "Excel", "Statistics"));

            service.addOpportunity(
                    "Campus Innovation Challenge",
                    "Student Cell",
                    "Hackathon",
                    today.plusDays(3),
                    2,
                    5000,
                    List.of("Java", "Problem Solving", "Git"));
        } catch (InvalidInputException e) {
            System.out.println("Could not create demo data: " + e.getMessage());
        }
    }

    private void run() {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> showAll();
                    case "2" -> showRecommendations();
                    case "3" -> addOpportunity();
                    case "4" -> apply();
                    case "5" -> showApplications();
                    case "6" -> updateApplication();
                    case "7" -> startReminder();
                    case "8" -> ReflectionCatalog.printFeatures();
                    case "9" -> deleteOpportunity();
                    case "0" -> {
                        running = false;
                        System.out.println("Thank you for using OPPORTUNA.");
                    }
                    default -> System.out.println("Please choose a valid menu option.");
                }
            } catch (InvalidInputException | DateTimeParseException e) {
                System.out.println("Input error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Input error: please enter a valid number.");
            } catch (RuntimeException e) {
                String message = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
                System.out.println("Unexpected error: " + message);
            }
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n============= OPPORTUNA =============");
        System.out.println("Student: " + profile);
        System.out.println("1. View opportunities");
        System.out.println("2. Get skill + deadline recommendations");
        System.out.println("3. Add an opportunity");
        System.out.println("4. Apply to an opportunity");
        System.out.println("5. View applications");
        System.out.println("6. Update application status");
        System.out.println("7. Check deadline reminders (multithreaded)");
        System.out.println("8. Show technical features (reflection)");
        System.out.println("9. Delete an opportunity");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private void showAll() {
        List<Opportunity> opportunities = service.all();

        System.out.println("\n--- Opportunities ---");
        if (opportunities.isEmpty()) {
            System.out.println("No opportunities found.");
            return;
        }

        opportunities.forEach(System.out::println);
    }

    private void showRecommendations() {
        List<Opportunity> recommendations = service.rankedFor(profile);

        System.out.println("\n--- Opportunity Recommendations ---");
        if (recommendations.isEmpty()) {
            System.out.println("No opportunities available.");
            return;
        }

        for (Opportunity opportunity : recommendations) {
            int match = service.matchScore(profile, opportunity);
            int priority = service.priority(profile, opportunity);
            List<String> matched = service.matchedSkills(profile, opportunity);
            List<String> missing = service.missingSkills(profile, opportunity);

            System.out.println("\n" + opportunity);
            System.out.println("Matched skills: " + (matched.isEmpty() ? "None" : matched));
            System.out.println("Missing skills: " + (missing.isEmpty() ? "None" : missing));
            System.out.println("Skill match: " + match + "%");
            System.out.println("Priority: " + priority + "/100");
        }
    }

    private void addOpportunity() throws InvalidInputException {
        String title = InputValidator.text(ask("Title"), "Title");
        String organization = InputValidator.text(ask("Organization"), "Organization");
        String type = InputValidator.text(ask("Type"), "Type");

        LocalDate deadline = LocalDate.parse(ask("Deadline (YYYY-MM-DD)"));
        int duration = InputValidator.nonNegativeInt(ask("Duration in weeks"), "Duration");
        int stipend = InputValidator.nonNegativeInt(ask("Stipend in INR"), "Stipend");
        List<String> skills = parseSkills(ask("Required skills (comma separated)"));

        service.addOpportunity(title, organization, type, deadline, duration, stipend, skills);
        System.out.println("Opportunity added successfully.");
    }

    private void apply() throws InvalidInputException {
        long id = InputValidator.positiveLong(ask("Opportunity ID"), "Opportunity ID");
        String note = ask("Note (optional)");

        Application application = service.apply(id, note);
        System.out.println("Application created successfully:");
        System.out.println(application);
    }

    private void showApplications() {
        List<Application> applications = service.applications();

        System.out.println("\n--- Applications ---");
        if (applications.isEmpty()) {
            System.out.println("No applications yet.");
            return;
        }

        applications.forEach(System.out::println);
    }

    private void updateApplication() throws InvalidInputException {
        long id = InputValidator.positiveLong(ask("Application ID"), "Application ID");

        System.out.println("1. SAVED");
        System.out.println("2. APPLIED");
        System.out.println("3. SHORTLISTED");
        System.out.println("4. INTERVIEW");
        System.out.println("5. REJECTED");
        System.out.println("6. SELECTED");

        int number = InputValidator.nonNegativeInt(ask("New status number"), "Status");
        if (number < 1 || number > ApplicationStatus.values().length) {
            throw new InvalidInputException("Invalid status number.");
        }

        ApplicationStatus status = ApplicationStatus.values()[number - 1];
        String note = ask("Updated note (leave blank to keep old note)");
        service.updateApplication(id, status, note);
        System.out.println("Application updated successfully.");
    }

    private void startReminder() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new ReminderWorker(service.all()));
        executor.shutdown();
        System.out.println("Reminder worker started in a separate thread.");
    }

    private void deleteOpportunity() throws InvalidInputException {
        long id = InputValidator.positiveLong(ask("Opportunity ID to delete"), "Opportunity ID");
        service.deleteOpportunity(id);
    }

    private String ask(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine();
    }

    private List<String> parseSkills(String raw) {
        if (raw == null || raw.isBlank()) {
            return new ArrayList<>();
        }

        Vector<String> skills = new Vector<>();
        for (String item : raw.split(",")) {
            String clean = item.trim();
            if (!clean.isBlank() && skills.stream().noneMatch(existing ->
                    existing.equalsIgnoreCase(clean))) {
                skills.add(clean);
            }
        }
        return new ArrayList<>(skills);
    }
}

