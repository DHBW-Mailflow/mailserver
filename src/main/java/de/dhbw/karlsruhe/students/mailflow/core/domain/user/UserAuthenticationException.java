package de.dhbw.karlsruhe.students.mailflow.core.domain.user;

public class UserAuthenticationException extends RuntimeException{
    public UserAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
