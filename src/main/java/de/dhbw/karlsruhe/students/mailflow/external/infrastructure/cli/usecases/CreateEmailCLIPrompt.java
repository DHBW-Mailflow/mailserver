package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LoginUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.SendEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AbstractCLIPrompt;

public class CreateEmailCLIPrompt extends AbstractCLIPrompt {
  private final LoginUseCase loginUseCase;
  private final SendEmailUseCase sendEmailUseCase;

  public CreateEmailCLIPrompt(LoginUseCase loginUseCase, SendEmailUseCase sendEmailUseCase) {
    this.loginUseCase = loginUseCase;
    this.sendEmailUseCase = sendEmailUseCase;
  }

  public void start() {
    String recipientsTo = simplePrompt("Who are the recipients (to)? - comma seperated");
    String recipientsCC = simplePrompt("Who are the recipients (cc)? - comma seperated");
    String recipientsBCC = simplePrompt("Who are the recipients (bcc)? - comma seperated");
    String subject = simplePrompt("What is the email subject?");
    String content = simplePrompt("What is the email content?");
    Address sender = loginUseCase.getSessionUser().email();

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
