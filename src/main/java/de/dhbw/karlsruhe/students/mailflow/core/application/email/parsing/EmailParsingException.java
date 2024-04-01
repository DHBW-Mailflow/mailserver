package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;

/**
 * @author jens1o
 */
public class EmailParsingException extends RuntimeException {
    public EmailParsingException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
