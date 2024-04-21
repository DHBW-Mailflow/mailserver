package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.UCCollectionSettings;
import java.util.LinkedHashMap;
import java.util.Map;

public class SettingsCLIPrompt extends BaseCLIPrompt {

  private final UCCollectionSettings collectionSettings;

  public SettingsCLIPrompt(MainCLIPrompt mainCLIPrompt, UCCollectionSettings collectionSettings) {
    super(mainCLIPrompt);
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
    return readUserInputWithOptions(promptMap);
  }
}