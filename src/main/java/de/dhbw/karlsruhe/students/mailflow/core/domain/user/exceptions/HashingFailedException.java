package de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions;

public class HashingFailedException extends Exception{
    public HashingFailedException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
