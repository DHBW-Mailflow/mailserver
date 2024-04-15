package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jens1o
 */
public final class ComposeEmailCLIPrompt extends BaseCLIPrompt {

    private final AuthUseCase authUseCase;
    private final EmailSendUseCase emailSendUseCase;

    public ComposeEmailCLIPrompt(AuthUseCase authUseCase, EmailSendUseCase emailSendUseCase) {
        this.authUseCase = authUseCase;
        this.emailSendUseCase = emailSendUseCase;
    }

    @Override
    public void start() {
        super.start();

        User sender = authUseCase.getSessionUser();

        if (sender == null) {
            printWarning("You must be logged in to send a mail.");
            return;
        }

        List<Address> toRecipients = getAddressesFromPromptResponse(
                simplePrompt("Please write the TO-recipients (separated by a comma):"));
        List<Address> ccRecipients = getAddressesFromPromptResponse(
                simplePrompt("Please write the CC-recipients (separated by a comma):"));
        List<Address> bccRecipients = getAddressesFromPromptResponse(
                simplePrompt("Please write the BCC-recipients (separated by a comma):"));

        if (toRecipients.isEmpty() && ccRecipients.isEmpty() && bccRecipients.isEmpty()) {
            printWarning("You need to specify at least one valid recipient!");
            return;
        }

        String subject = simplePrompt("What is the subject?");
        subject = subject.trim();

    printDefault(
        "Please write your message now: (To finish, please press Ctrl + D on Linux / Windows)");
        String message = readMultilineUserInput();
        message = message.trim();

        Email email = Email.Builder.buildEmail(subject, sender.email(), toRecipients, ccRecipients,
                bccRecipients, message);

        try {
      emailSendUseCase.sendEmail(email);
      printDefault("Email sent successfully!");
        } catch (MailboxLoadingException | MailboxSavingException e) {
            printWarning("Error sending mail, try again later.");
        }
    }

    private List<Address> getAddressesFromPromptResponse(String promptResponse) {
        ArrayList<Address> result = new ArrayList<>();

        for (String addressCandidate : promptResponse.split(",")) {
            try {
                addressCandidate = addressCandidate.trim();

                if (addressCandidate.isEmpty()) {
                    continue;
                }

                result.add(Address.from(addressCandidate));
            } catch (IllegalArgumentException e) {
                printWarning(addressCandidate + " is not a valid e-mail address, ignoring!");
            }
        }

        return result;
    }
}
