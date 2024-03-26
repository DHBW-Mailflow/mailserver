package de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.reader;


import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxParsingException;

public interface MailboxParser {

 Mailbox parseMailbox(String content)throws MailboxParsingException;
}
