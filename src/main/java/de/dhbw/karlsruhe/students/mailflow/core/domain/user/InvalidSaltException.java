package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

public class InvalidSaltException extends RuntimeException{
    public InvalidSaltException(String errorMessage) {
        super(errorMessage);
    }
}
