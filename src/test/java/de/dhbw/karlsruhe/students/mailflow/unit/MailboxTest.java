package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.*;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.fest.assertions.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class MailboxTest {
  @Test
  public void testGetEmailsWithLabel() {
    // Arrange
    Address owner = new Address("owner", "domain.de");
    Address emailSender = new Address("sender", "otherDomain.de");
    Recipients recipients = new Recipients(List.of(owner), List.of(), List.of());
    Subject subject = new Subject("Subject");
    EmailMetadata metadata =
        new EmailMetadata(subject, emailSender, List.of(), recipients, SentDate.ofNow());
    Email unreadEmail = Email.create("firstContent", metadata);
    Email readEmail = Email.create("secondContent", metadata);

    Map<Email, Set<Label>> mails = new HashMap<>();
    mails.put(unreadEmail, Set.of(Label.UNREAD));
    mails.put(readEmail, Set.of(Label.READ));
    Mailbox mailbox = Mailbox.create(owner, mails, MailboxType.INBOX);

    // Act
    List<Email> emails = mailbox.getEmailsWithLabel(Label.UNREAD);

    // Assert

    Assertions.assertThat(emails).hasSize(1).contains(unreadEmail).doesNotContain(readEmail);
  }

  @Test
  public void testGetEmailsLabel() {
    // Arrange
    Address owner = new Address("owner", "domain.de");
    Address emailSender = new Address("sender", "otherDomain.de");
    Recipients recipients = new Recipients(List.of(owner), List.of(), List.of());
    Subject subject = new Subject("Subject");
    EmailMetadata metadata =
        new EmailMetadata(subject, emailSender, List.of(), recipients, SentDate.ofNow());
    Email unreadEmail = Email.create("firstContent", metadata);
    Email readEmail = Email.create("secondContent", metadata);

    Map<Email, Set<Label>> mails = new HashMap<>();
    mails.put(unreadEmail, Set.of(Label.UNREAD));
    mails.put(readEmail, Set.of(Label.READ));
    Mailbox mailbox = Mailbox.create(owner, mails, MailboxType.INBOX);

    // Act
    List<Email> emails = mailbox.getEmailList();

    // Assert

    Assertions.assertThat(emails).hasSize(2).contains(unreadEmail).contains(readEmail);
  }

  @Test
  public void testDeliverEmail() {
    // Arrange
    Address owner = new Address("owner", "domain.de");
    Address emailSender = new Address("sender", "otherDomain.de");
    Recipients recipients = new Recipients(List.of(owner), List.of(), List.of());
    Subject subject = new Subject("Subject");
    EmailMetadata metadata =
        new EmailMetadata(subject, emailSender, List.of(), recipients, SentDate.ofNow());
    Email unreadEmail = Email.create("firstContent", metadata);
    Email readEmail = Email.create("secondContent", metadata);
    Email emailToDeliver = Email.create("emailToDeliver", metadata);

    Map<Email, Set<Label>> mails = new HashMap<>();
    mails.put(unreadEmail, Set.of(Label.UNREAD));
    mails.put(readEmail, Set.of(Label.READ));
    Mailbox mailbox = Mailbox.create(owner, mails, MailboxType.INBOX);

    // Act
    mailbox.deliverEmail(emailToDeliver, true);
    List<Email> emails = mailbox.getEmailsWithLabel(Label.UNREAD);

    // Assert
    Assertions.assertThat(emails).hasSize(2).contains(unreadEmail).contains(emailToDeliver);
  }

  @ParameterizedTest(name = "should create correct mailbox with type {0}")
  @EnumSource(MailboxType.class)
  public void testGetType(MailboxType mailboxType) {
    // Arrange
    Address owner = new Address("owner", "domain.de");

    // Act
    Mailbox mailbox = Mailbox.create(owner, new HashMap<>(), mailboxType);

    // Assert
    assertEquals(mailbox.getType(), mailboxType);
  }

  @ParameterizedTest(name = "should create correct mailbox with type {0}")
  @EnumSource(MailboxType.class)
  public void testGetOwner(MailboxType mailboxType) {
    // Arrange
    Address owner = new Address("owner", "domain.de");

    // Act
    Mailbox mailbox = Mailbox.create(owner, new HashMap<>(), mailboxType);

    // Assert
    assertEquals(mailbox.getOwner(), owner);
  }

  @Test
  public void testMarkWithLabel() {
    // Arrange
    Address owner = new Address("owner", "domain.de");
    Address emailSender = new Address("sender", "otherDomain.de");
    Recipients recipients = new Recipients(List.of(owner), List.of(), List.of());
    Subject subject = new Subject("Subject");
    EmailMetadata metadata =
        new EmailMetadata(subject, emailSender, List.of(), recipients, SentDate.ofNow());
    Email unreadEmail = Email.create("firstContent", metadata);
    Email readEmail = Email.create("secondContent", metadata);

    Map<Email, Set<Label>> mails = new HashMap<>();
    mails.put(unreadEmail, Set.of(Label.UNREAD));
    mails.put(readEmail, Set.of(Label.READ));
    Mailbox mailbox = Mailbox.create(owner, mails, MailboxType.INBOX);

    // Act
    mailbox.markWithLabel(readEmail, Label.UNREAD);
    mailbox.markWithLabel(unreadEmail, Label.READ);
    List<Email> unreadEmails = mailbox.getEmailsWithLabel(Label.UNREAD);
    List<Email> readEmails = mailbox.getEmailsWithLabel(Label.READ);

    // Assert
    Assertions.assertThat(unreadEmails).hasSize(1).contains(readEmail);
    Assertions.assertThat(readEmails).hasSize(1).contains(unreadEmail);
  }
}
