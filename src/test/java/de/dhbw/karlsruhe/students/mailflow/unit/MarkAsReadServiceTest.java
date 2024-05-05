package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.mark.MarkAsReadService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.fest.assertions.api.Assertions;
import org.junit.jupiter.api.Test;

class MarkAsReadServiceTest {
  private static MarkAsReadService getMarkAsReadService(
      Address loggedInUser, Mailbox mailboxOfUser) {
    AuthSessionUseCase mockedAuthSession = new MockedAuthorizedSession(loggedInUser);

    MailboxRepository mockedMailboxRepository = new MockedMailboxRepository(mailboxOfUser);

    return new MarkAsReadService(mockedAuthSession, mockedMailboxRepository);
  }

  @Test
  void testMarkAsRead() throws MailboxSavingException, MailboxLoadingException {
    // Arrange
    Address loggedInUser = new Address("some", "domain.de");
    EmailMetadata emailMetadata =
        new EmailMetadata(
            new Subject("someSubject"), loggedInUser, List.of(), null, SentDate.ofNow());
    Email emailToMark = Email.create("content", emailMetadata);
    Map<Email, Set<Label>> mailboxEmails = new HashMap<>();
    mailboxEmails.put(emailToMark, Set.of(Label.UNREAD));
    Mailbox mailboxOfUser = Mailbox.create(loggedInUser, mailboxEmails, MailboxType.INBOX);

    MarkAsReadService markAsReadService = getMarkAsReadService(loggedInUser, mailboxOfUser);

    // Act
    markAsReadService.mark(emailToMark);
    List<Email> readEmails = mailboxOfUser.getEmailsWithLabel(Label.READ);

    // Assert
    Assertions.assertThat(readEmails).contains(emailToMark);
  }
}
