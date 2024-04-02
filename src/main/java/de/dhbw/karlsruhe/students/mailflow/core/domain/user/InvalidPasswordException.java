package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String errorMessage) {
        super(errorMessage);
    }
}
