package de.dhbw.karlsruhe.students.mailflow.core.application.imap;

import de.dhbw.karlsruhe.students.mailflow.core.domain.imap.ImapListenerConfig;
import de.dhbw.karlsruhe.students.mailflow.core.domain.imap.ImapListenerException;

public interface ImapListener {
    void configure(ImapListenerConfig config);

    void listen() throws ImapListenerException;

    void stop();
}
