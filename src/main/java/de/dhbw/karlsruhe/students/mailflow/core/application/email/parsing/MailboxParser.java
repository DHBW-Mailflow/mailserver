package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;


import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import java.io.InputStream;
import java.nio.file.Path;

public interface MailboxParser {

public Mailbox parseMailbox(Path path) throws MailboxParsingException;
}
