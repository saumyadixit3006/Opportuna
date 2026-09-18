package com.opportuna.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final String course;
    private final ArrayList<String> skills;

    public StudentProfile(String name, String course, List<String> skills) {
        this.name = name;
        this.course = course;
        this.skills = new ArrayList<>(skills);
    }

    public String getName() {
        return name;
    }

    public String getCourse() {
        return course;
    }

    public List<String> getSkills() {
        return new ArrayList<>(skills);
    }

    @Override
    public String toString() {
        return name + " | " + course + " | skills: " + skills;
    }
}

