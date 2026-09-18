package com.opportuna.util;

import com.opportuna.annotation.Feature;
import com.opportuna.service.MatchEngine;
import com.opportuna.service.PriorityEngine;

import java.util.List;

public final class ReflectionCatalog {
    private ReflectionCatalog() {
    }

    public static void printFeatures() {
        List<Class<?>> classes = List.of(
                MatchEngine.class,
                PriorityEngine.class
        );

        System.out.println("\n--- Technical Features Found Using Reflection ---");
        for (Class<?> clazz : classes) {
            Feature feature = clazz.getAnnotation(Feature.class);
            if (feature != null) {
                System.out.println("* " + clazz.getSimpleName() + ": " + feature.value());
            }
        }
    }
}

