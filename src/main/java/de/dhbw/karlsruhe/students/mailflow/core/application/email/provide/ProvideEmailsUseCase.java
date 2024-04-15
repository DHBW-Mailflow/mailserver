package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

public interface ProvideEmailsUseCase {

  List<Email> provideReadEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException;

  List<Email> provideUnreadEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException;

  void markEmailAsRead(Email email, Address address)
      throws MailboxSavingException, MailboxLoadingException;

  List<Email> provideSpamEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException;

  List<Email> provideDeletedEmails(Address address)
      throws MailboxSavingException, MailboxLoadingException;


}
