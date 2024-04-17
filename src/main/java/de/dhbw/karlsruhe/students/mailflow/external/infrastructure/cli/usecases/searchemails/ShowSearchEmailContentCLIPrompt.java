package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

public class ShowSearchEmailContentCLIPrompt extends BaseCLIPrompt {

  private final Email email;

  public ShowSearchEmailContentCLIPrompt(Email email) {
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
