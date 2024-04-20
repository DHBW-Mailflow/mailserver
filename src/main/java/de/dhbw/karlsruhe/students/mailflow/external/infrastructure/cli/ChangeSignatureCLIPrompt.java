package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureUseCase;
import java.io.FileNotFoundException;

public class ChangeSignatureCLIPrompt extends BaseCLIPrompt {

  private final ChangeSignatureUseCase changeSignatureUseCase;

  public ChangeSignatureCLIPrompt(
      SettingsCLIPrompt settingsCLIPrompt, ChangeSignatureUseCase changeSignatureUseCase) {
    super(settingsCLIPrompt);
    this.changeSignatureUseCase = changeSignatureUseCase;
  }

  @Override
  public void start() {
    super.start();
    try {
      String newSignature = simplePrompt("Enter your new signature");
      changeSignatureUseCase.updateSignature(newSignature);
      BaseCLIPrompt baseCLIPrompt = showActionMenuPrompt();
      baseCLIPrompt.start();
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    return getPreviousPrompt();
  }
}
