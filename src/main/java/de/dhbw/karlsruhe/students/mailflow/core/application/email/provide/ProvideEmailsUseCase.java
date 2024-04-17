package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

public interface ProvideEmailsUseCase {

  void markEmailAsRead(Email email, Address address, MailboxType mailboxType)
      throws MailboxSavingException, MailboxLoadingException;

  List<Email> provideEmails(Address sessionUserAddress, MailboxType mailboxType, Label... labels)
      throws MailboxSavingException, MailboxLoadingException;
}
