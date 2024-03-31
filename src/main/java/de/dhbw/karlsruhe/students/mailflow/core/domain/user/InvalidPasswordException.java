package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

public class InvalidPasswordException extends UserException{
    public InvalidPasswordException(String errorMessage) {
        super(errorMessage);
    }
}
