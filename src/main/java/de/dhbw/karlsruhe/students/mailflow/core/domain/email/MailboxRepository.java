package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MailboxRepository {
  /**
   * @param address the address of the mailbox owner
   * @param type the type of the mailbox owner
   * @return the mailbox object with the provided address and type
   * @throws MailboxLoadingException when the mailbox could not be loaded
   * @throws MailboxSavingException when the mailbox could not be implicitly created
   */
  Mailbox findByAddressAndType(Address address, MailboxType type)
      throws MailboxLoadingException, MailboxSavingException;

  /**
   * @param mailbox the mailbox to be stored
   * @throws MailboxSavingException when a error occurred during the saving process
   */
  void save(Mailbox mailbox) throws MailboxSavingException;

  List<Email> findAllEmails();
}
