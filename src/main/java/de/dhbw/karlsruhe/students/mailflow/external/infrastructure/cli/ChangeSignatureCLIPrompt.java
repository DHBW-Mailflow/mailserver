package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import java.io.FileNotFoundException;

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
      printDefault("Enter new signature:");
      String newSignature = readMultilineUserInput();
      changeSignatureUseCase.updateSignature(newSignature);
      printDefault("Signature updated successfully");
      getPreviousPrompt().start();
    } catch (LoadSettingsException e) {
      printWarning("Failed to update signature");
    }
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    return getPreviousPrompt();
  }
}
