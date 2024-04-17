package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideInboxUnreadEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProvideUnreadEmailsTest {

  @Test
  void provideUnreadEmailsTest() throws MailboxSavingException, MailboxLoadingException {

    // Arrange
    Address address = new Address("test", "test");
    MailboxType mailboxType = MailboxType.INBOX;
    Email email =
        Email.Builder.buildEmail("test", address, List.of(), List.of(), List.of(), "test message");

    Map<Email, Set<Label>> mailboxMap = Map.of(email, Set.of(Label.UNREAD));
    Mailbox mailbox = Mailbox.create(address, mailboxMap, mailboxType);
    MailboxRepository mailboxRepository =
        new MailboxRepository() {
          @Override
          public Mailbox findByAddressAndType(Address address, MailboxType type) {
            return mailbox;
          }

          @Override
          public void save(Mailbox mailbox) {
            // not tested
          }
        };

    ProvideEmailsUseCase provideEmailsService =
        new ProvideInboxUnreadEmailsService(mailboxRepository);

    // Act
    List<Email> emailList = provideEmailsService.provideEmails(address);

    // Assert
    Assertions.assertEquals(1, emailList.size());

    Email emailFromList = emailList.get(0);

    Assertions.assertEquals(email, emailFromList);
  }
}
