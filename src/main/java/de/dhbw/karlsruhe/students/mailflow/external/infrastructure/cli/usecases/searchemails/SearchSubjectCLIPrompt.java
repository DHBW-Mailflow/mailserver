package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.searchemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchContentEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.subject.SearchSubjectEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

public class SearchSubjectCLIPrompt extends BaseCLIPrompt {
  public SearchSubjectCLIPrompt(
      SearchEmailCLIPrompt searchEmailCLIPrompt,
      AuthUseCase authUseCase,
      SearchSubjectEmailUseCase searchSubjectEmailUseCase) {
    super();
  }
}
