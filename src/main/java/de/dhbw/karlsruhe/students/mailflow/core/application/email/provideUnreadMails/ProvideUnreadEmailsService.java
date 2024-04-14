package de.dhbw.karlsruhe.students.mailflow.core.application.email.provideUnreadMails;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProvideUnreadEmailsService implements ProvideUnreadEmailsUseCase{

  private final Mailbox mailbox;

  public ProvideUnreadEmailsService(Mailbox mailbox) {
    this.mailbox = mailbox;
  }

  @Override
  public List<Email> provideUnreadEmails() {
    return new ArrayList<>(mailbox.getUnreadEmails());
  }
}
