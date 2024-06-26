package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author seiferla , Jonas-Karl
 */
public class SearchBeforeDateEmailService implements SearchEmailUseCase {

  private final ProvideEmailsUseCase provideEmailsUseCase;

  public SearchBeforeDateEmailService(ProvideEmailsUseCase provideEmailsUseCase) {
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public List<Email> searchEmails(String content)
      throws MailboxSavingException, MailboxLoadingException, DateTimeException {

    List<Email> emailList = provideEmailsUseCase.provideEmails();

    LocalDate sentDate = HelperParsing.parseDate(content);

    return emailList.stream()
        .filter(email -> email.getSendDate().date().toLocalDate().isBefore(sentDate))
        .toList();
  }
}
