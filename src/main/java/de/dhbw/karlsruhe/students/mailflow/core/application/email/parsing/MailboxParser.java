package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;


import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;

public interface MailboxParser {

 Mailbox parseMailbox(String content);
}
