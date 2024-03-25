package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.MailboxFileProvider;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class MailboxParsingService {

    private final MailboxFileProvider mailboxFileProvider;

    private final MailboxParser mailboxParser;

    public MailboxParsingService(MailboxFileProvider mailboxRepository,
            MailboxParser mailboxParser) {
        this.mailboxFileProvider = mailboxRepository;
        this.mailboxParser = mailboxParser;
    }

    public Mailbox getMailboxOfAddress(Address address) throws MailboxParsingServiceException {
        Optional<File> storedFile =
                mailboxFileProvider.provideStoredMailboxFileFor(address, MailboxType.READ);
        if (storedFile.isEmpty()) {
            throw new MailboxParsingServiceException("File does not exist");
        }
        String content = getContent(storedFile.get());
        return mailboxParser.parseMailbox(content);
    }

    private String getContent(File file) throws MailboxParsingServiceException {
        String content;

        try (FileReader fileReader = new FileReader(file)) {
            content = fileReader.toString();
        } catch (IOException e) {
            throw new MailboxParsingServiceException("File could not be read");
        }
        if (content == null) {
            throw new MailboxParsingServiceException("File could not be read");
        }
        return content;
    }
}
