package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

public class InvalidEmailException extends UserException {
    public InvalidEmailException(String errorMessage) {
        super(errorMessage);
    }
}
