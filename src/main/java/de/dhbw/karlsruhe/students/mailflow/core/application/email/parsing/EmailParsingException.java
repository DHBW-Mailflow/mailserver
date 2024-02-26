package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;

public class EmailParsingException extends RuntimeException {
    public EmailParsingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
