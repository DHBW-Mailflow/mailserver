package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.spam.strategies;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.rules.SpamDetectionStrategy;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

/**
 * Detects spam by checking whether the sender has sent an e-mail before
 *
 * @author jens1o
 */
public class ReputationAnalysisSpamDetectionStrategy implements SpamDetectionStrategy {

  private final MailboxRepository mailboxRepository;

  public ReputationAnalysisSpamDetectionStrategy(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public String getReason() {
    return "sender is not known";
  }

  @Override
  public boolean isSpam(Email email) throws MailboxSavingException, MailboxLoadingException {
    Address sender = email.getSender();

    List<Mailbox> otherMailboxes = mailboxRepository.findAllOtherInboxes(sender);
    long emailWithSenderCounter = 0;

    for (Mailbox otherMailbox : otherMailboxes) {
      List<Email> inboxEmails = otherMailbox.getEmailList();
      emailWithSenderCounter +=
          inboxEmails.stream().filter(mail -> mail.getSender().equals(sender)).count();
    }

    return emailWithSenderCounter == 0;
  }
}
