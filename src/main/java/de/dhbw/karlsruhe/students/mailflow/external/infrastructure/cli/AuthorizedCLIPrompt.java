package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;

public class AuthorizedCLIPrompt extends BaseCLIPrompt {
  public final AuthUseCase authUseCase;

  public AuthorizedCLIPrompt(AuthUseCase authUseCase) {
    if (authUseCase == null) {
      throw new IllegalArgumentException("AuthUseCase must not be null");
    }
    this.authUseCase = authUseCase;
  }

  @Override
  public void start() {
    super.start();
    authUseCase.ensureLoggedIn();
  }
}
