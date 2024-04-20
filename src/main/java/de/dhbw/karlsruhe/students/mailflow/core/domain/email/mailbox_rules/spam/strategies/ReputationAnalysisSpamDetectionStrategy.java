package de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.spam.strategies;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

/**
 * Detects spam by checking whether the sender has sent an e-mail before
 *
 * @author jens1o
 */
public class ReputationAnalysisSpamDetectionStrategy implements SpamDetectionStrategy {

  @Override
  public String getHumanReadableReason() {
    return "sender is not known";
  }

  @Override
  public boolean isSpam(MailboxRepository mailboxRepository, Email email) {
    Address sender = email.getSender();

    try {
      for (Mailbox mailbox : mailboxRepository.findAll()) {
        List<Email> emails = mailbox.getEmailList();

        for (Email seenEmails : emails) {
          if (seenEmails.getSender() == sender) {
            return false;
          }
        }
      }
    } catch (MailboxLoadingException | MailboxSavingException e) {
      // no-op
    }

    return true;
  }

}
