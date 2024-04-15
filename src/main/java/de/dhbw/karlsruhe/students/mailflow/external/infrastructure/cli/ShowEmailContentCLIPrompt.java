package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;

public class ShowEmailContentCLIPrompt extends BaseCLIPrompt {

  private final Email email;

  public ShowEmailContentCLIPrompt(Email email) {
    this.email = email;
  }
  @Override
  public void start() {
    printDefault(formatEmailContent(email));
  }

  public String formatEmailContent(Email email) {
    return "%s".formatted(email.getContent());
  }
}
