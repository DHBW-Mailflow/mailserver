package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonas-Karl
 */
public class ProvideAllEmailsService extends AbstractProvideEmailsService {

  ProvideAllEmailsService(MailboxRepository mailboxRepository) {
    super(mailboxRepository);
  }

  @Override
  public List<Email> provideEmails(Address mailboxOwner)
      throws MailboxSavingException, MailboxLoadingException {

    List<Email> allEmails = new ArrayList<>();
    for (MailboxType type : MailboxType.values()) {
      Mailbox mailbox = mailboxRepository.findByAddressAndType(mailboxOwner, type);
      allEmails.addAll(mailbox.getEmailList());
    }

    return allEmails;
  }

  @Override
  public String getMailboxName() {
    return "all";
  }
}
