package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.parsing.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxDoesNotExistException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public interface MailboxRepository {
  Mailbox findByAddressAndType(Address address, MailboxType type)
      throws MailboxDoesNotExistException, MailboxLoadingException;

  void save(Mailbox mailbox) throws MailboxSavingException;
}
