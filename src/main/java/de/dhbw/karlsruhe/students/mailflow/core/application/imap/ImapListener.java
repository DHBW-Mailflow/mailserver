package de.dhbw.karlsruhe.students.mailflow.core.application.imap;

public interface ImapListener {
    public void configure(ImapListenerConfig config);

    public void listen() throws Exception;

    public void stop();
}
