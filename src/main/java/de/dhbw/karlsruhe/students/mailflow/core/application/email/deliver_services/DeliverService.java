package de.dhbw.karlsruhe.students.mailflow.core.application.email.deliver_services;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public interface DeliverService {
  /**
   * applies the matching rule that should be applied according to the MailboxRule
   *
   * @param recipient the recipient of the email
   * @param email the email to deliver
   * @throws MailboxLoadingException when an error occurred during the load process
   * @throws MailboxSavingException when an error occurred during the save process
   */
  void deliverEmailTo(Address recipient, Email email)
      throws MailboxLoadingException, MailboxSavingException;
}
