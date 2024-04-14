package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli;

import de.dhbw.karlsruhe.students.mailflow.core.application.auth.LoginUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.SendEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;

public class CreateEmailCLIPrompt extends AbstractCLIPrompt {
    private LoginUseCase loginUseCase;
    private SendEmailUseCase sendEmailUseCase;

    public CreateEmailCLIPrompt(LoginUseCase loginUseCase, SendEmailUseCase sendEmailUseCase) {
        this.loginUseCase = loginUseCase;
        this.sendEmailUseCase = sendEmailUseCase;
    }

    private String showRecipientToPrompt() {
        System.out.println("Who are the recipients (to)? - comma seperated");
        return readUserInput();
    }

    private String showRecipientCCPrompt() {
        System.out.println("Who are the recipients (cc)? - comma seperated");
        return readUserInput();
    }

    private String showRecipientBCCPrompt() {
        System.out.println("Who are the recipients (bcc)? - comma seperated");
        return readUserInput();
    }


    //TODO move domain object creation to gateway or application layer?
    public void start() {
        String recipientTo = showRecipientToPrompt();
        String recipientsCC = showRecipientCCPrompt();
        String recipientsBCC = showRecipientBCCPrompt();
        String subject = simplePrompt("What is the email subject?");
        String content = simplePrompt("What is the email content?");
        Address sender = loginUseCase.getSessionUser().email();

        Email email = sendEmailUseCase.prepareEmail(sender, subject, content, recipientTo, recipientsCC, recipientsBCC);
        sendEmailUseCase.send(email);

    }
}
