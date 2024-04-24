package de.dhbw.karlsruhe.students.mailflow.unit;

import static org.junit.jupiter.api.Assertions.*;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideAllEmailsService;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProvideAllEmailsServiceTest {

  final Address mailboxOwner = new Address("some", "domain.de");

  @Test
  void provideEmails() throws MailboxSavingException, MailboxLoadingException {
    final AuthSessionUseCase mockedAuth = new MockedAuthorizedSession(mailboxOwner);
    final Map<MailboxType, Mailbox> allMailboxes = new HashMap<>();
    for (MailboxType type : MailboxType.values()) {
      Map<Email, Set<Label>> emailMap = new HashMap<>();
      emailMap.put(getEmail(type), Set.of(Label.READ));
      allMailboxes.put(type, Mailbox.create(mailboxOwner, emailMap, type));
    }

    final ProvideAllEmailsService service = getProvideAllEmailsService(allMailboxes, mockedAuth);

    List<Email> allEmails = service.provideEmails();

    assertEquals(allEmails.size(), MailboxType.values().length);
  }

  private Email getEmail(MailboxType type) {
    Recipients recipients = new Recipients(List.of(mailboxOwner), List.of(), List.of());
    Subject subject = new Subject("Subject");
    EmailMetadata metadata =
        new EmailMetadata(subject, mailboxOwner, List.of(), recipients, SentDate.ofNow());
    return Email.create("Email of inbox %s".formatted(type.getStoringName()), metadata);
  }

  @Test
  void getMailboxName() {
    final AuthSessionUseCase mockedAuth = new MockedAuthorizedSession(mailboxOwner);

    final ProvideAllEmailsService service = getProvideAllEmailsService(Map.of(), mockedAuth);

    String name = service.getMailboxName();

    assertEquals("all", name);
  }

  private static ProvideAllEmailsService getProvideAllEmailsService(
      Map<MailboxType, Mailbox> allMailboxes, AuthSessionUseCase mockedAuth) {
    final MailboxRepository mockedMailboxRepository =
        new MailboxRepository() {
          @Override
          public Mailbox findByAddressAndType(Address address, MailboxType type) {
            return allMailboxes.get(type);
          }

          @Override
          public void save(Mailbox mailbox) {
            // not tested
          }

          @Override
          public List<Mailbox> findAllOtherInboxes(Address sender) {
            // not tested
            return List.of();
          }
        };
    return new ProvideAllEmailsService(mockedAuth, mockedMailboxRepository);
  }
}
