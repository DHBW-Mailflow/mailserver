package de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String errorMessage) {
        super(errorMessage);
    }
}
