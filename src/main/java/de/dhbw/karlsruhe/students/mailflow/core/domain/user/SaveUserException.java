package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

public class SaveUserException extends Exception{
    public SaveUserException(String errorMessage) {
        super(errorMessage);
    }
    public SaveUserException (String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
