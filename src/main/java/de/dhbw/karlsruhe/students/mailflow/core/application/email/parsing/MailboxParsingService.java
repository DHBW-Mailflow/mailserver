package de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public class MailboxParsingService {

  private final MailboxRepository mailboxRepository;

  private final MailboxParser mailboxParser;

  public MailboxParsingService(MailboxRepository mailboxRepository, MailboxParser mailboxParser) {
    this.mailboxRepository = mailboxRepository;
    this.mailboxParser = mailboxParser;
  }

  public Mailbox getMailboxOfAddress(Address address) throws MailboxParsingServiceException {
    var storedFile = mailboxRepository.provideStoredMailboxFileFor(address);
    if (storedFile.isEmpty()){
      throw new MailboxParsingServiceException("File does not exist");
    }
    return mailboxParser.parseMailbox(storedFile.get(),address);

  }
}
