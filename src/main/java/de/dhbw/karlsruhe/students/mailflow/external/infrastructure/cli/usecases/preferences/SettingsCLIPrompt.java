package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.preferences;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.UCCollectionSettings;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.MainCLIPrompt;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author seiferla
 */
public class SettingsCLIPrompt extends BaseCLIPrompt {

  private final UCCollectionSettings collectionSettings;

  public SettingsCLIPrompt(BaseCLIPrompt previousPrompt, UCCollectionSettings collectionSettings) {
    super(previousPrompt);
    this.collectionSettings = collectionSettings;
  }

  @Override
  public void start() {
    super.start();
    BaseCLIPrompt action = showActionMenuPrompt();
    action.start();
  }

  private BaseCLIPrompt showActionMenuPrompt() {
    printDefault("Which setting do you want to change?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    promptMap.put(
        "Change signature",
        new ChangeSignatureCLIPrompt(this, collectionSettings.changeSignatureService()));
    promptMap.put(
        "Reset signature",
        new ResetSignatureCLIPrompt(this, collectionSettings.changeSignatureService()));
    return readUserInputWithOptions(promptMap);
  }
}
