package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchContentEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date.SearchAfterDateEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date.SearchBeforeDateEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date.SearchEqualDateEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.recipient.SearchRecipientEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.sender.SearchSenderEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.subject.SearchSubjectEmailService;

public record UCCollectionSearchEmail(
    SearchContentEmailService searchContentEmailUseCase,
    SearchSubjectEmailService searchSubjectEmailUseCase,
    SearchAfterDateEmailService searchAfterDateEmailService,
    SearchBeforeDateEmailService searchBeforeDateEmailService,
    SearchEqualDateEmailService searchEqualDateEmailService,
    SearchRecipientEmailService searchRecipientEmailService,
    SearchSenderEmailService searchSenderEmailService) {

  public static UCCollectionSearchEmail init(ProvideEmailsUseCase provideEmailsUseCase) {
    return new UCCollectionSearchEmail(
        new SearchContentEmailService(provideEmailsUseCase),
        new SearchSubjectEmailService(provideEmailsUseCase),
        new SearchAfterDateEmailService(provideEmailsUseCase),
        new SearchBeforeDateEmailService(provideEmailsUseCase),
        new SearchEqualDateEmailService(provideEmailsUseCase),
        new SearchRecipientEmailService(provideEmailsUseCase),
        new SearchSenderEmailService(provideEmailsUseCase));
  }
}
