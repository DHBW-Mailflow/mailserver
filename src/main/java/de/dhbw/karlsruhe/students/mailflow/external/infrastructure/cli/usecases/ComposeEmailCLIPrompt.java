package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.InvalidRecipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author jens1o
 */
public final class ComposeEmailCLIPrompt extends BaseCLIPrompt {

  public static final String INVALID_RECIPIENT_FORMAT = "Invalid recipient format";
  private final EmailSendUseCase emailSendUseCase;

  public ComposeEmailCLIPrompt(BaseCLIPrompt previousPrompt, EmailSendUseCase emailSendUseCase) {
    super(previousPrompt);
    this.emailSendUseCase = emailSendUseCase;
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
    emailSendUseCase.setMessage(message);
  }

  private void askRecipients() {
    askRecipientTo();
    askRecipientCC();
    askRecipientBCC();
  }

  private void askRecipientTo() {
    String recipientsString =
        simplePrompt("Please write the TO-recipients (separated by a comma):").trim();
    try {
      emailSendUseCase.setToRecipients(recipientsString);
    } catch (IllegalArgumentException e) {
      printWarning(INVALID_RECIPIENT_FORMAT);
      askRecipientTo();
    }
  }

  private void askRecipientCC() {
    String recipientsString =
        simplePrompt("Please write the CC-recipients (separated by a comma):").trim();
    try {
      emailSendUseCase.setCCRecipients(recipientsString);
    } catch (IllegalArgumentException e) {
      printWarning(INVALID_RECIPIENT_FORMAT);
      askRecipientCC();
    }
  }

  private void askRecipientBCC() {
    String recipientsString =
        simplePrompt("Please write the BCC-recipients (separated by a comma):").trim();
    try {
      emailSendUseCase.setBCCRecipients(recipientsString);
    } catch (IllegalArgumentException e) {
      printWarning(INVALID_RECIPIENT_FORMAT);
      askRecipientBCC();
    }
  }
}
