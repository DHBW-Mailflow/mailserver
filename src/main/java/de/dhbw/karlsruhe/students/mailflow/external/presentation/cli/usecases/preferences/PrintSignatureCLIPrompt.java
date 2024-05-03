package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases.preferences;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureUseCase;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.BaseCLIPrompt;

public class PrintSignatureCLIPrompt extends BaseCLIPrompt {

  private final ChangeSignatureUseCase changeSignatureUseCase;

  public PrintSignatureCLIPrompt(
      BaseCLIPrompt previousPrompt, ChangeSignatureUseCase changeSignatureUseCase) {
    super(previousPrompt);
    this.changeSignatureUseCase = changeSignatureUseCase;
  }

  @Override
  public void start() {
    super.start();
    try {
      String currentSignature = changeSignatureUseCase.getSignature();
      printDefault("Current signature:\n" + currentSignature);
    } catch (Exception e) {
      printWarning("Failed to get signature");
    }
    getPreviousPrompt().start();
  }
}
