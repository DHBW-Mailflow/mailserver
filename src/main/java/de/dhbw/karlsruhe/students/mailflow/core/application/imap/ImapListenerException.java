package de.dhbw.karlsruhe.students.mailflow.core.application.imap;

public class ImapListenerException extends RuntimeException {
    public ImapListenerException(String message, Throwable cause) {
        super(message, cause);
    }
}
