package de.dhbw.karlsruhe.students.mailflow.core.domain.auth;

public class AuthorizationException extends Exception {

    public AuthorizationException(String errorMessage) {
        super(errorMessage);
    }
}
