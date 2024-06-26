package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideInboxUnreadEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.EmailBuilder;
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
  private final Address address = new Address("test", "test");

  private final AuthSessionUseCase mockedAuth = new MockedAuthorizedSession(address);

  @Test
  void provideUnreadEmailsTest() throws MailboxSavingException, MailboxLoadingException {

    // Arrange
    MailboxType mailboxType = MailboxType.INBOX;
    Email email =
        new EmailBuilder()
            .withSubject("test")
            .withSender(address)
            .withContent("test message")
            .build();

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

          @Override
          public List<Mailbox> findAllOtherInboxes(Address sender) {
            throw new UnsupportedOperationException("Unimplemented method 'findAll'");
          }
        };

    ProvideEmailsUseCase provideEmailsService =
        new ProvideInboxUnreadEmailsService(mockedAuth, mailboxRepository);

    // Act
    List<Email> emailList = provideEmailsService.provideEmails();

    // Assert
    Assertions.assertEquals(1, emailList.size());

    Email emailFromList = emailList.getFirst();

    Assertions.assertEquals(email, emailFromList);
  }
}
