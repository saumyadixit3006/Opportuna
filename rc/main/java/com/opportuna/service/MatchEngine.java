package com.opportuna.service;

import com.opportuna.annotation.Feature;
import com.opportuna.model.Opportunity;
import com.opportuna.model.StudentProfile;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Feature("Skill matching using normalized sets")
public class MatchEngine {

    public int score(StudentProfile profile, Opportunity opportunity) {
        Set<String> studentSkills = normalize(profile.getSkills());
        Set<String> requiredSkills = normalize(opportunity.getRequiredSkills());

        if (requiredSkills.isEmpty()) {
            return 0;
        }

        long matches = requiredSkills.stream()
                .filter(studentSkills::contains)
                .count();

        return (int) Math.round((matches * 100.0) / requiredSkills.size());
    }

    public List<String> matchedSkills(StudentProfile profile, Opportunity opportunity) {
        Set<String> studentSkills = normalize(profile.getSkills());
        return normalize(opportunity.getRequiredSkills()).stream()
                .filter(studentSkills::contains)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> missingSkills(StudentProfile profile, Opportunity opportunity) {
        Set<String> studentSkills = normalize(profile.getSkills());
        return normalize(opportunity.getRequiredSkills()).stream()
                .filter(skill -> !studentSkills.contains(skill))
                .sorted()
                .collect(Collectors.toList());
    }

    private Set<String> normalize(List<String> skills) {
        Set<String> normalized = new TreeSet<>();
        for (String skill : skills) {
            if (skill != null) {
                String clean = skill.trim().toLowerCase(Locale.ROOT);
                if (!clean.isBlank()) {
                    normalized.add(clean);
                }
            }
        }
        return normalized;
    }
}

