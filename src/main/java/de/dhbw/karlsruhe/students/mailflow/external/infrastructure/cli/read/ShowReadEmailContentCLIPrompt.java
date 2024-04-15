package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.read;


import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;

import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

public class ShowReadEmailContentCLIPrompt extends BaseCLIPrompt {

  private final Email email;

  public ShowReadEmailContentCLIPrompt(Email email) {
    this.email = email;
  }

  @Override
  public void start() {
    printDefault(formatEmailContent(email));
  }

  private String formatEmailContent(Email email) {
    return "%s".formatted(email.getContent());
  }
}
