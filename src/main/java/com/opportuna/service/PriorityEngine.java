package com.opportuna.service;

import com.opportuna.annotation.Feature;
import com.opportuna.model.Opportunity;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Feature("Deadline-aware opportunity priority calculation")
public class PriorityEngine {
    private final Clock clock;

    public PriorityEngine() {
        this(Clock.systemDefaultZone());
    }

    public PriorityEngine(Clock clock) {
        this.clock = clock;
    }

    public int priority(Opportunity opportunity, int matchScore) {
        long days = ChronoUnit.DAYS.between(
                LocalDate.now(clock), opportunity.getDeadline());

        int urgency;
        if (days < 0) {
            urgency = 0;
        } else if (days <= 2) {
            urgency = 40;
        } else if (days <= 7) {
            urgency = 30;
        } else if (days <= 14) {
            urgency = 20;
        } else {
            urgency = 10;
        }

        int fit = Math.round(matchScore * 0.6f);
        int stipendValue = Math.min(opportunity.getStipend() / 1000, 20);

        return Math.min(100, fit + urgency + stipendValue);
    }
}
