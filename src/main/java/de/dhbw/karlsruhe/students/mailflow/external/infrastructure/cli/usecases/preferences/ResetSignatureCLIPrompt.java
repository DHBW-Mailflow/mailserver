package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.preferences;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.ChangeSignatureUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveSettingsException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author seiferla
 */
public class ResetSignatureCLIPrompt extends BaseCLIPrompt {

  private final ChangeSignatureUseCase changeSignatureUseCase;

  public ResetSignatureCLIPrompt(
      BaseCLIPrompt previousPrompt, ChangeSignatureUseCase changeSignatureUseCase) {
    super(previousPrompt);
    this.changeSignatureUseCase = changeSignatureUseCase;
  }

  @Override
  public void start() {

    try {
      super.start();
      changeSignatureUseCase.resetSignature();
      printDefault("Signature removed successfully");
      getPreviousPrompt().start();
    } catch (LoadSettingsException | SaveSettingsException e) {
      printWarning("Failed to remove signature");
    }
  }
}
