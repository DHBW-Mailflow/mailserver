package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.GenericSearchEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.ArrayList;
import java.util.List;

public class SearchContentEmailService extends GenericSearchEmailService {


  public SearchContentEmailService(MailboxRepository mailboxRepository) {
    super(mailboxRepository);
  }
}
