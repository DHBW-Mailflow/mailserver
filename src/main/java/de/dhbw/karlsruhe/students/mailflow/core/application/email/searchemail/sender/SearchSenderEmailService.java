package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.sender;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.util.List;

/**
 * @author seiferla , Jonas-Karl
 */
public class SearchSenderEmailService implements SearchEmailUseCase {

  private final ProvideEmailsUseCase provideEmailsUseCase;

  public SearchSenderEmailService(ProvideEmailsUseCase provideEmailsUseCase) {
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public List<Email> searchEmails(String content)
      throws MailboxSavingException, MailboxLoadingException {
    List<Email> emailList = provideEmailsUseCase.provideEmails();
    return emailList.stream()
        .filter(email -> email.getSender().toString().contains(content))
        .toList();
  }
}
