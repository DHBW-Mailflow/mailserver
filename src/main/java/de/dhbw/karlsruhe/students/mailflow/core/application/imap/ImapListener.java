package de.dhbw.karlsruhe.students.mailflow.core.application.imap;

import de.dhbw.karlsruhe.students.mailflow.core.domain.imap.ImapListenerConfig;
import de.dhbw.karlsruhe.students.mailflow.core.domain.imap.ImapListenerException;

public interface ImapListener {
    public void configure(ImapListenerConfig config);

    public void listen() throws ImapListenerException;

    public void stop();
}
