package de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.send.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.usersettings.UCCollectionSettings;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.InvalidRecipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.LoadSettingsException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.exceptions.SaveSettingsException;
import de.dhbw.karlsruhe.students.mailflow.external.presentation.cli.BaseCLIPrompt;
import java.time.ZonedDateTime;

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
    askScheduledSend();
    sendEmail();
  }

  private void askScheduledSend() {
    while (true) {
      String userResponse = simplePrompt(
          "In case you want to schedule sending the e-mail at a later time,"
              + " please now specify the time to send the mail [leave empty for immediate sending, pattern YYYY-MM-DD HH:mm]:");

      if (userResponse.isEmpty()) {
        break;
      }

      ZonedDateTime scheduledDate;
      try {
        scheduledDate =
            ucCollectionSettings.scheduledSendTimeParserUseCase()
                .parseScheduledSendDateTime(userResponse);
      } catch (IllegalArgumentException e) {
        printWarning("Invalid time provided: %s".formatted(e.getMessage()));
        continue;
      }

      emailSendUseCase.setScheduledSendDate(scheduledDate);
      break;
    }
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
        simplePrompt("Please write the TO-recipients (separated by a comma):");
    try {
      emailSendUseCase.setToRecipients(recipientsString);
    } catch (InvalidRecipients e) {
      printWarning(e.getMessage());
      askRecipientTo();
    }
  }

  private void askRecipientCC() {
    String recipientsString =
        simplePrompt("Please write the CC-recipients (separated by a comma):");
    try {
      emailSendUseCase.setCCRecipients(recipientsString);
    } catch (InvalidRecipients e) {
      printWarning(e.getMessage());
      askRecipientCC();
    }
  }

  private void askRecipientBCC() {
    String recipientsString =
        simplePrompt("Please write the BCC-recipients (separated by a comma):");
    try {
      emailSendUseCase.setBCCRecipients(recipientsString);
    } catch (InvalidRecipients e) {
      printWarning(e.getMessage());
      askRecipientBCC();
    }
  }
}
