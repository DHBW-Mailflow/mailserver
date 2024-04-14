package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.SendEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AbstractCLIPrompt;

public class CreateEmailCLIPrompt extends AbstractCLIPrompt {
  private final AuthUseCase authUseCase;
  private final SendEmailUseCase sendEmailUseCase;

  public CreateEmailCLIPrompt(AuthUseCase authUseCase, SendEmailUseCase sendEmailUseCase) {
    this.authUseCase = authUseCase;
    this.sendEmailUseCase = sendEmailUseCase;
  }

  public void start() {
    String recipientsTo = simplePrompt("Who are the recipients (to)? - comma seperated");
    String recipientsCC = simplePrompt("Who are the recipients (cc)? - comma seperated");
    String recipientsBCC = simplePrompt("Who are the recipients (bcc)? - comma seperated");
    String subject = simplePrompt("What is the email subject?");
    String content = simplePrompt("What is the email content?");
    Address sender = authUseCase.getSessionUser().email();

    Email email = null;
    try {
      email =
          sendEmailUseCase.prepareEmail(
              sender, subject, content, recipientsTo, recipientsCC, recipientsBCC);
    } catch (IllegalArgumentException e) {
      System.err.println(e.getMessage());
    }
    sendEmailUseCase.send(email);
  }
}
