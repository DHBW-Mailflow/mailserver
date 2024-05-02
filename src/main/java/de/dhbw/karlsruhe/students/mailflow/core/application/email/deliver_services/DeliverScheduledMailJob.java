package de.dhbw.karlsruhe.students.mailflow.core.application.email.deliver_services;

import java.time.ZonedDateTime;
import java.util.List;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.rules.MailboxRule;
import de.dhbw.karlsruhe.students.mailflow.core.application.job.Job;
import de.dhbw.karlsruhe.students.mailflow.core.application.job.JobExecutionException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public class DeliverScheduledMailJob implements Job {

  private List<Address> recipients;
  private MailboxRule mailboxRule;
  private Email email;
  private ZonedDateTime sendDate;


  public DeliverScheduledMailJob(List<Address> recipients, Email email, MailboxRule mailboxRule,
      ZonedDateTime sendDate) {
    this.recipients = recipients;
    this.email = email;
    this.mailboxRule = mailboxRule;
    this.sendDate = sendDate;
  }

  @Override
  public ZonedDateTime getReadyTime() {
    return sendDate;
  }

  @Override
  public void perform() throws JobExecutionException {
    try {
      DeliverUseCase deliverUseCase = mailboxRule.runOnEmail(email);
      for (Address recipient : recipients) {
        deliverUseCase.deliverEmailTo(recipient, email);
      }
    } catch (Exception e) {
      throw new JobExecutionException(e);
    }
  }

}
