package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.email;

public class EmailParsingException extends RuntimeException {
    public EmailParsingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
