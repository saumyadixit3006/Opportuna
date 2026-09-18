package com.opportuna.thread;

import com.opportuna.model.Opportunity;
import com.opportuna.util.ActivityLog;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReminderWorker implements Runnable {
    private final List<Opportunity> opportunities;
    private final Clock clock;

    public ReminderWorker(List<Opportunity> opportunities) {
        this(opportunities, Clock.systemDefaultZone());
    }

    public ReminderWorker(List<Opportunity> opportunities, Clock clock) {
        this.opportunities = List.copyOf(opportunities);
        this.clock = clock;
    }

    @Override
    public void run() {
        LocalDate today = LocalDate.now(clock);
        boolean found = false;

        System.out.println("\n--- Deadline Reminder Worker ---");

        for (Opportunity opportunity : opportunities) {
            long days = ChronoUnit.DAYS.between(today, opportunity.getDeadline());

            if (days >= 0 && days <= 3) {
                found = true;
                String message = "REMINDER: " + opportunity.getTitle()
                        + " closes in " + days + " day(s).";
                System.out.println(message);
                ActivityLog.write(message);
            }
        }

        if (!found) {
            System.out.println("No opportunity is due within the next 3 days.");
        }
    }
}

