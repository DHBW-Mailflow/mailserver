package de.dhbw.karlsruhe.students.mailflow.core.domain.imap;

/**
 * @author jens1o
 */
public class ImapListenerException extends RuntimeException {
    public ImapListenerException(String message, Throwable cause) {
        super(message, cause);
    }
}
