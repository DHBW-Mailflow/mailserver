package de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions;

/**
 * @author seiferla
 */
public class SaveUserException extends Exception {
    public SaveUserException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
