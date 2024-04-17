package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchContentEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.subject.SearchSubjectEmailUseCase;
import de.dhbw.karlsruhe.students.mailflow.external.infrastructure.email.parsing.FileMailboxRepository;

public record UCCollectionSearchEmail(
    SearchContentEmailUseCase searchContentEmailUseCase,
    SearchSubjectEmailUseCase searchSubjectEmailUseCase) {

  public static UCCollectionSearchEmail init(FileMailboxRepository mailboxRepository) {
    return new UCCollectionSearchEmail(
        new SearchContentEmailUseCase(mailboxRepository),
        new SearchSubjectEmailUseCase(mailboxRepository));
  }


}
