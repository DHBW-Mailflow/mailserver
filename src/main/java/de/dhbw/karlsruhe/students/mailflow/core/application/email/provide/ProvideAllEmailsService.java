package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author seiferla, Jonas-Karl
 */
public class ProvideAllEmailsService implements ProvideEmailsUseCase {
  private final MailboxRepository mailboxRepository;
  private final AuthSessionUseCase authSession;

  public ProvideAllEmailsService(
      AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
    this.authSession = authSession;
  }

  @Override
  public List<Email> provideEmails() throws MailboxSavingException, MailboxLoadingException {
    List<Email> allEmails = new ArrayList<>();
    for (MailboxType type : MailboxType.values()) {
      Mailbox mailbox =
          mailboxRepository.findByAddressAndType(authSession.getSessionUserAddress(), type);
      allEmails.addAll(mailbox.getEmailList());
    }

    return allEmails;
  }

  @Override
  public String getMailboxName() {
    return "all";
  }

  public int getEmailCount() {
    try {
      return provideEmails().size();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      return 0;
    }
  }
}
