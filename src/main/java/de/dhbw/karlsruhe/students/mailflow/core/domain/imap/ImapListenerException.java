package de.dhbw.karlsruhe.students.mailflow.core.domain.imap;

public class ImapListenerException extends RuntimeException {
    public ImapListenerException(String message, Throwable cause) {
        super(message, cause);
    }
}
