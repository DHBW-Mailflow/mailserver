package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.SentDate;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SearchEqualDateEmailService implements SearchEmailUseCase {

  private final ProvideEmailsUseCase provideEmailsUseCase;

  public SearchEqualDateEmailService(ProvideEmailsUseCase provideEmailsUseCase) {
    this.provideEmailsUseCase = provideEmailsUseCase;
  }

  @Override
  public List<Email> searchEmails(String content, Address address)
      throws MailboxSavingException,
          MailboxLoadingException,
          DateTimeException,
          DateTimeParseException {

    List<Email> emailList = provideEmailsUseCase.provideEmails(address);

    SentDate sentDate = SentDate.ofFormattedString(content);

    return emailList.stream()
        .filter(email -> email.getSendDate().date().isEqual(sentDate.date()))
        .toList();
  }
}
