package de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.usecases.showemails;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.answer.UCCollectionAnswerEmails;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.MarkEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.cli.BaseCLIPrompt;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author seiferla
 */
public class ShowEmailsCLIPrompt extends BaseCLIPrompt {

  private final ProvideEmailsUseCase provideEmailsUseCase;
  private final MarkEmailUseCase markEmailUseCase;
  private final UCCollectionAnswerEmails answerEmails;

  ShowEmailsCLIPrompt(
      BaseCLIPrompt previousPrompt,
      ProvideEmailsUseCase provideEmailsUseCase,
      MarkEmailUseCase markEmailUseCase,
      UCCollectionAnswerEmails answerEmails) {
    super(previousPrompt);
    this.provideEmailsUseCase = provideEmailsUseCase;
    this.markEmailUseCase = markEmailUseCase;
    this.answerEmails = answerEmails;
  }

  @Override
  public void start() {
    super.start();
    String mailboxString = provideEmailsUseCase.getMailboxName();
    printDefault("This are your %s emails:".formatted(mailboxString));
    try {
      List<Email> emailList = provideEmailsUseCase.provideEmails();
      BaseCLIPrompt action = showActionMenuPrompt(emailList);
      action.start();
    } catch (MailboxSavingException | MailboxLoadingException e) {
      printWarning("Could not read %s emails".formatted(mailboxString));
    }
  }

  private BaseCLIPrompt showActionMenuPrompt(List<Email> emailList) {
    if (emailList.isEmpty()) {
      printDefault("No emails found");
      return getPreviousPrompt();
    }
    printDefault("Which email do you want to read?");
    Map<String, BaseCLIPrompt> promptMap = new LinkedHashMap<>();
    for (Email email : emailList) {
      promptMap.put(
          formatEmailListing(email),
          new ReadEmailContentCLIPrompt(
              this, email, markEmailUseCase, answerEmails, provideEmailsUseCase));
    }
    return readUserInputWithOptions(promptMap);
  }
}
