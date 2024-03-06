package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;


import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;

public interface MailboxParser {

public Mailbox parseMailbox(File file, Address address) throws MailboxParsingException;
}
