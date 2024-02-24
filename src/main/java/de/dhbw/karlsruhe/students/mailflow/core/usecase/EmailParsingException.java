package de.dhbw.karlsruhe.students.mailflow.core.usecase;

public class EmailParsingException extends RuntimeException {
    public EmailParsingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
