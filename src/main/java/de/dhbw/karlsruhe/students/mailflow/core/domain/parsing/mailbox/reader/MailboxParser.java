package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.reader;


import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;

public interface MailboxParser {

 Mailbox parseMailbox(String content);
}
