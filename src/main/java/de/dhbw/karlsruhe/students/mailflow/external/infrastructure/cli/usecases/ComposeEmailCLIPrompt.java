package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.UCCollectionSettings;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.changesignature.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.InvalidRecipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.preferences.SaveSettingsException;

/**
 * @author jens1o
 */
public final class ComposeEmailCLIPrompt extends BaseCLIPrompt {

  private final EmailSendUseCase emailSendUseCase;
  private final UCCollectionSettings ucCollectionSettings;

  public ComposeEmailCLIPrompt(
      BaseCLIPrompt previousPrompt,
      EmailSendUseCase emailSendUseCase,
      UCCollectionSettings ucCollectionSettings) {
    super(previousPrompt);
    this.emailSendUseCase = emailSendUseCase;
    this.ucCollectionSettings = ucCollectionSettings;
  }

  @Override
  public void start() {
    super.start();
    askRecipients();
    if (!validRecipients()) {
      return;
    }
    askTextContent();
    sendEmail();
  }

  private void sendEmail() {
    try {
      emailSendUseCase.sendPreparedEmail();
      printDefault("Email sent successfully!");
    } catch (MailboxLoadingException | MailboxSavingException e) {
      printWarning("Error sending mail, try again later.");
    } catch (InvalidRecipients e) {
      printWarning("You need to specify at least one valid recipient!");
    }
  }

  private boolean validRecipients() {
    try {
      emailSendUseCase.validateRecipients();
    } catch (InvalidRecipients e) {
      printWarning("You need to specify at least one valid recipient!");
      return false;
    }
    return true;
  }

  private void askTextContent() {
    String subject = simplePrompt("What is the subject?");
    emailSendUseCase.setSubject(subject);
    printDefault("Please write your message now: (To finish, please write :q on a new line)");
    String message = readMultilineUserInput();
    try {
      message = appendSignature(message);
    } catch (LoadSettingsException | SaveSettingsException e) {
      printWarning("Could not load signature, sending mail without signature.");
    }
    emailSendUseCase.setMessage(message);
  }

  private void askRecipients() {
    askRecipientTo();
    askRecipientCC();
    askRecipientBCC();
  }

  private String appendSignature(String message)
      throws LoadSettingsException, SaveSettingsException {
    String userSettings = ucCollectionSettings.changeSignatureService().getSignature();
    return message + "\n" + userSettings;
  }

  private void askRecipientTo() {
    String recipientsString =
        simplePrompt("Please write the TO-recipients (separated by a comma):").trim();
    try {
      emailSendUseCase.setToRecipients(recipientsString);
    } catch (InvalidRecipients e) {
      printWarning(e.getMessage());
      askRecipientTo();
    }
  }

  private void askRecipientCC() {
    String recipientsString =
        simplePrompt("Please write the CC-recipients (separated by a comma):").trim();
    try {
      emailSendUseCase.setCCRecipients(recipientsString);
    } catch (InvalidRecipients e) {
      printWarning(e.getMessage());
      askRecipientCC();
    }
  }

  private void askRecipientBCC() {
    String recipientsString =
        simplePrompt("Please write the BCC-recipients (separated by a comma):").trim();
    try {
      emailSendUseCase.setBCCRecipients(recipientsString);
    } catch (InvalidRecipients e) {
      printWarning(e.getMessage());
      askRecipientBCC();
    }
  }
}
