package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.AuthorizedCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

public class ShowSearchEmailContentCLIPrompt extends AuthorizedCLIPrompt {

  private final Email email;

  public ShowSearchEmailContentCLIPrompt(BaseCLIPrompt previousPrompt, AuthUseCase auth, Email email) {
    super(previousPrompt, auth);
    this.email = email;
  }

  @Override
  public void start() {
    printDefault(formatEmailContent(email));
  }

  public String formatEmailContent(Email email) {
    return email.getContent();
  }
}
