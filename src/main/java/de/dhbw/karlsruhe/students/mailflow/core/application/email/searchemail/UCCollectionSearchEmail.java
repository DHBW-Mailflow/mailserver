package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchContentEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.subject.SearchSubjectEmailService;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.FileMailboxRepository;

public record UCCollectionSearchEmail(
    SearchContentEmailService searchContentEmailUseCase,
    SearchSubjectEmailService searchSubjectEmailUseCase) {

  public static UCCollectionSearchEmail init(FileMailboxRepository mailboxRepository) {
    return new UCCollectionSearchEmail(
        new SearchContentEmailService(mailboxRepository), // (1)
        new SearchSubjectEmailService(mailboxRepository)); // (2);
  }


}
