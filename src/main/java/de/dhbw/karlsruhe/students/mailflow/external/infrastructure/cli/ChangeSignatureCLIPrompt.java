package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.authorization.SaveSettingsException;

public class ChangeSignatureCLIPrompt extends BaseCLIPrompt {

  private final ChangeSignatureUseCase changeSignatureUseCase;

  public ChangeSignatureCLIPrompt(
      BaseCLIPrompt baseCLIPrompt, ChangeSignatureUseCase changeSignatureUseCase) {
    super(baseCLIPrompt);
    this.changeSignatureUseCase = changeSignatureUseCase;
  }

  @Override
  public void start() {

    try {
      super.start();
      String currentSignature = changeSignatureUseCase.getSignature();
      printDefault("Current signature:\n"+currentSignature);
      printDefault("Enter new signature:  (To finish, please write :q on a new line)");
      String newSignature = readMultilineUserInput();
      changeSignatureUseCase.updateSignature(newSignature);
      printDefault("Signature updated successfully");
      getPreviousPrompt().start();
    } catch (LoadSettingsException | SaveSettingsException e) {
      printWarning("Failed to update signature");
    }
  }
}
