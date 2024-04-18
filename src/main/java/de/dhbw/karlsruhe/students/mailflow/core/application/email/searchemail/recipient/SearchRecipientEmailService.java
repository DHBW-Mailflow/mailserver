package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.recipient;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

/**
 * @author seiferla , Jonas-Karl
 */
public class SearchRecipientEmailService implements SearchEmailUseCase {

  final ProvideEmailsUseCase provideEmailsUseCase;

  public SearchRecipientEmailService(ProvideEmailsUseCase provideEmailsUseCase) {
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public List<Email> searchEmails(String content, Address address)
      throws MailboxSavingException, MailboxLoadingException {
    List<Email> emailList = provideEmailsUseCase.provideEmails(address);
    return emailList.stream().filter(email -> getAllRecipients(email).contains(content)).toList();
  }

  String getAllRecipients(Email email) {
    String recipients = email.getRecipientCC().toString();
    recipients += email.getRecipientBCC().toString();
    recipients += email.getRecipientTo().toString();
    return recipients;
  }
}
