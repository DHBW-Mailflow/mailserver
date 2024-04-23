package de.dhbw.karlsruhe.students.mailflow.core.application.email.spam.strategies;

import com.google.common.net.InternetDomainName;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

/**
 * @author jens1o
 */
public class UnusualSenderSpamDetectionStrategy implements SpamDetectionStrategy {

  @Override
  public boolean isSpam(MailboxRepository mailboxRepository, Email email) {
    Address sender = email.getSender();

    return !InternetDomainName.isValid(sender.domain());
  }

  @Override
  public String getHumanReadableReason() {
    return "sender is not on a valid domain name";
  }

}
