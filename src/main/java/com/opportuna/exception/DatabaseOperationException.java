package com.opportuna.exception;

/**
 * Represents a database/JDBC operation failure.
 */
public class DatabaseOperationException extends Exception {
    private static final long serialVersionUID = 1L;

    public DatabaseOperationException(String message) {
        super(message);
    }

    public DatabaseOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
