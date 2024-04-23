package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.mailbox_rules.spam;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.deliver_services.DeliverInInboxService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.deliver_services.DeliverIntoSpamService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.deliver_services.DeliverService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.rules.MailboxRule;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.rules.SpamDetectionStrategy;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
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
  public DeliverService runOnEmail(Email email)
      throws MailboxSavingException, MailboxLoadingException {

    for (SpamDetectionStrategy strategy : strategies) {
      if (strategy.isSpam(email)) {
        return new DeliverIntoSpamService(repository, strategy.getReason());
      }
    }

    return new DeliverInInboxService(repository);
  }
}
