package com.opportuna.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public final class ActivityLog {
    private static final Path LOG = Path.of("data", "activity.log");

    private ActivityLog() {
    }

    public static synchronized void write(String message) {
        try {
            Files.createDirectories(LOG.getParent());
            Files.writeString(
                    LOG,
                    LocalDateTime.now() + " | " + message + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Log warning: " + e.getMessage());
        }
    }
}
