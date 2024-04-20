package de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.spam;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.MailboxRule;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.result.DeliverInInbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.result.MailboxRuleResult;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.result.MarkAsSpam;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.spam.strategies.ContentAnalysisSpamDetectionStrategy;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.spam.strategies.ReputationAnalysisSpamDetectionStrategy;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.spam.strategies.SpamDetectionStrategy;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.mailbox_rules.spam.strategies.UnusualSenderSpamDetectionStrategy;

/**
 * @author jens1o
 */
public class DetectSpamOnIncomingMailMailboxRule implements MailboxRule {

  @Override
  public MailboxRuleResult runOnEmail(MailboxRepository repository, Email email) {
    SpamDetectionStrategy[] strategies = new SpamDetectionStrategy[] {
        new ContentAnalysisSpamDetectionStrategy(), new ReputationAnalysisSpamDetectionStrategy(),
        new UnusualSenderSpamDetectionStrategy()};

    for (SpamDetectionStrategy strategy : strategies) {
      if (strategy.isSpam(repository, email)) {
        return new MarkAsSpam().becauseOf(strategy.getHumanReadableReason());
      }
    }

    return new DeliverInInbox();
  }

}
