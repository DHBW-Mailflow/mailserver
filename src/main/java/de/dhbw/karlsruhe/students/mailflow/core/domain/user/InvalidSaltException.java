package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

public class InvalidSaltException extends UserException{
    public InvalidSaltException(String errorMessage) {
        super(errorMessage);
    }
}
