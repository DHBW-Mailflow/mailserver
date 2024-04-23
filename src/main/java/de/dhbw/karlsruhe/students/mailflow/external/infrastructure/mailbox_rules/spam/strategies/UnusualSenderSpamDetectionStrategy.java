package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.spam.strategies;

import com.google.common.net.InternetDomainName;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.rules.SpamDetectionStrategy;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

/**
 * @author jens1o
 */
public class UnusualSenderSpamDetectionStrategy implements SpamDetectionStrategy {

  @Override
  public boolean isSpam(Email email) {
    Address sender = email.getSender();

    return !InternetDomainName.isValid(sender.domain());
  }

  @Override
  public String getReason() {
    return "sender is not on a valid domain name";
  }
}
