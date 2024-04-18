package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
/**
 * @author seiferla , Jonas-Karl

 */
public class SearchAfterDateEmailService implements SearchEmailUseCase {

  private final ProvideEmailsUseCase provideEmailsUseCase;

  public SearchAfterDateEmailService(ProvideEmailsUseCase provideEmailsUseCase) {
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public List<Email> searchEmails(String content, Address address)
      throws MailboxSavingException,
          MailboxLoadingException,
          DateTimeException{

    List<Email> emailList = provideEmailsUseCase.provideEmails(address);

    LocalDate sentDate = HelperParsing.parseDate(content);

    return emailList.stream()
        .filter(email -> email.getSendDate().date().toLocalDate().isAfter(sentDate))
        .toList();
  }
}
