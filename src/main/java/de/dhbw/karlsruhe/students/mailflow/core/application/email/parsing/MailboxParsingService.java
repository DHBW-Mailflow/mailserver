package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxDoesNotExistException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.parsing.mailbox.reader.MailboxParser;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MailboxParsingService {

  private final MailboxRepository mailboxRepository;

  private final MailboxParser mailboxParser;

  public MailboxParsingService(MailboxRepository mailboxRepository, MailboxParser mailboxParser) {
    this.mailboxRepository = mailboxRepository;
    this.mailboxParser = mailboxParser;
  }

  public Mailbox getMailboxOfAddress(Address address) throws MailboxParsingServiceException, MailboxDoesNotExistException {
    var storedFile = mailboxRepository.provideStoredMailboxFileFor(address, MailboxType.READ);
    String content = getContent(storedFile);
    return mailboxParser.parseMailbox(content);
  }

  private String getContent(File file) throws MailboxParsingServiceException {
    String content;

    try (FileReader fileReader = new FileReader(file)) {
      content = fileReader.toString();
    } catch (IOException e) {
      throw new MailboxParsingServiceException("File could not be read",e);
    }
    if (content == null) {
      throw new MailboxParsingServiceException(String.format("File content of %s was empty", file.getPath()));
    }
    return content;
  }
}
