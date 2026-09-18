package com.opportuna.exception;

/**
 * Thrown when user input or a requested operation violates OPPORTUNA rules.
 */
public class InvalidInputException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
