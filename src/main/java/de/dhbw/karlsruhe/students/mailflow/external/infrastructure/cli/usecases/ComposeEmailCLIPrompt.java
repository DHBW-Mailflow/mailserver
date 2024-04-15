package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases;

import java.util.ArrayList;
import java.util.List;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.EmailSendUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.EmailMetadata;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Recipients;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Subject;
import de.dhbw.karlsruhe.students.mailflow.core.domain.user.User;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;

/**
 * @author jens1o
 */
public class ComposeEmailCLIPrompt extends BaseCLIPrompt {

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

        printDefault("Please write your message now:");
        String message = readMultilineUserInput();
        message = message.trim();

        Email email = buildEmail(subject, sender.email(), toRecipients, ccRecipients, bccRecipients,
                message);

        try {
            emailSendUseCase.sendEmail(email);
        } catch (MailboxLoadingException | MailboxSavingException e) {
            printWarning("Error sending mail, try again later.");
            e.printStackTrace();
        }
    }


    private Email buildEmail(String subject, Address sender, List<Address> toRecipients,
            List<Address> ccRecipients, List<Address> bccRecipients, String message) {
        EmailMetadata metadata = new EmailMetadata(new Subject(subject), sender, null,
                new Recipients(toRecipients, ccRecipients, bccRecipients), SentDate.ofNow());

        return Email.create(message, metadata);
    }

    private List<Address> getAddressesFromPromptResponse(String promptResponse) {
        ArrayList<Address> result = new ArrayList<>();

        for (String addressCandidate : promptResponse.split(",")) {
            try {
                result.add(Address.from(addressCandidate.trim()));
            } catch (IllegalArgumentException e) {
            }
        }

        return result;
    }
}
