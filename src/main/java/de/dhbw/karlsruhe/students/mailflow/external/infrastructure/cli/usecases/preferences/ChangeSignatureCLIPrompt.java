package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.preferences;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveSettingsException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

public class ChangeSignatureCLIPrompt extends BaseCLIPrompt {

  private final ChangeSignatureUseCase changeSignatureUseCase;

  public ChangeSignatureCLIPrompt(
      BaseCLIPrompt baseCLIPrompt, ChangeSignatureUseCase changeSignatureUseCase) {
    super(baseCLIPrompt);
    this.changeSignatureUseCase = changeSignatureUseCase;
  }

  @Override
  public void start() {
    super.start();
    try {

      String currentSignature = changeSignatureUseCase.getSignature();
      printDefault("Current signature:\n" + currentSignature);

    } catch (LoadSettingsException | SaveSettingsException e) {
      printWarning("Failed to get signature");
    }
    printDefault("Enter new signature:  (To finish, please write :q on a new line)");
    String newSignature = readMultilineUserInput();
    try {
      changeSignatureUseCase.updateSignature(newSignature);
      printDefault("Signature updated successfully");
      getPreviousPrompt().start();
    } catch (LoadSettingsException | SaveSettingsException e) {
      printWarning("Failed to update signature");
    }
  }
}
