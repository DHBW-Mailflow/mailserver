package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.SearchEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

public class SearchEmailCLIPrompt extends BaseCLIPrompt {
  public SearchEmailCLIPrompt(AuthUseCase authUseCase, SearchEmailUseCase searchEmailUseCase) {
    super();
  }
}
