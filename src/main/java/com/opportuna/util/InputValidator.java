package com.opportuna.util;

import com.opportuna.exception.InvalidInputException;

public final class InputValidator {
    private InputValidator() {
    }

    public static String text(String value, String field) throws InvalidInputException {
        String clean = value == null ? "" : value.trim();
        if (clean.isEmpty()) {
            throw new InvalidInputException(field + " cannot be empty.");
        }
        return clean;
    }

    public static int nonNegativeInt(String value, String field) throws InvalidInputException {
        try {
            int number = Integer.parseInt(value.trim());
            if (number < 0) {
                throw new NumberFormatException();
            }
            return number;
        } catch (NumberFormatException e) {
            throw new InvalidInputException(field + " must be a non-negative integer.");
        }
    }

    public static long positiveLong(String value, String field) throws InvalidInputException {
        try {
            long number = Long.parseLong(value.trim());
            if (number <= 0) {
                throw new NumberFormatException();
            }
            return number;
        } catch (NumberFormatException e) {
            throw new InvalidInputException(field + " must be a positive integer.");
        }
    }
}
