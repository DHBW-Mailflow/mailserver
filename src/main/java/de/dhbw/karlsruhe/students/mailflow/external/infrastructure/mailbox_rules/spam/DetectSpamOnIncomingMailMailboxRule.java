package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.spam;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.rules.MailboxRule;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.rules.MailboxRuleResult;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.rules.SpamDetectionStrategy;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.result.DeliverInInbox;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.result.MarkAsSpam;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.spam.strategies.ContentAnalysisSpamDetectionStrategy;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.spam.strategies.ReputationAnalysisSpamDetectionStrategy;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.spam.strategies.UnusualSenderSpamDetectionStrategy;

/**
 * @author jens1o
 */
public class DetectSpamOnIncomingMailMailboxRule implements MailboxRule {

  private final MailboxRepository repository;
  private final SpamDetectionStrategy[] strategies;

  public DetectSpamOnIncomingMailMailboxRule(MailboxRepository repository) {
    this.repository = repository;
    this.strategies =
        new SpamDetectionStrategy[] {
          new ContentAnalysisSpamDetectionStrategy(),
          new ReputationAnalysisSpamDetectionStrategy(repository),
          new UnusualSenderSpamDetectionStrategy()
        };
  }

  @Override
  public MailboxRuleResult runOnEmail(Email email)
      throws MailboxSavingException, MailboxLoadingException {

    for (SpamDetectionStrategy strategy : strategies) {
      if (strategy.isSpam(email)) {
        return new MarkAsSpam(repository, strategy.getReason());
      }
    }

    return new DeliverInInbox(repository);
  }
}
