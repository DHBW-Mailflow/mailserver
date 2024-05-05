package de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail;

import de.dhbw.karlsruhe.students.mailflow.core.application.email.provide.ProvideEmailsUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.content.SearchContentEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date.SearchAfterDateEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date.SearchBeforeDateEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.date.SearchEqualDateEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.recipient.SearchRecipientEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.sender.SearchSenderEmailService;
import de.dhbw.karlsruhe.students.mailflow.core.application.email.searchemail.subject.SearchSubjectEmailService;
import java.util.Objects;

/**
 * @author seiferla , Jonas-Karl
 */
public final class UCCollectionSearchEmail {
  private final SearchContentEmailService searchContentEmailUseCase;
  private final SearchSubjectEmailService searchSubjectEmailUseCase;
  private final SearchAfterDateEmailService searchAfterDateEmailService;
  private final SearchBeforeDateEmailService searchBeforeDateEmailService;
  private final SearchEqualDateEmailService searchEqualDateEmailService;
  private final SearchRecipientEmailService searchRecipientEmailService;
  private final SearchSenderEmailService searchSenderEmailService;

  /** */
  public UCCollectionSearchEmail(
      SearchContentEmailService searchContentEmailUseCase,
      SearchSubjectEmailService searchSubjectEmailUseCase,
      SearchAfterDateEmailService searchAfterDateEmailService,
      SearchBeforeDateEmailService searchBeforeDateEmailService,
      SearchEqualDateEmailService searchEqualDateEmailService,
      SearchRecipientEmailService searchRecipientEmailService,
      SearchSenderEmailService searchSenderEmailService) {
    this.searchContentEmailUseCase = searchContentEmailUseCase;
    this.searchSubjectEmailUseCase = searchSubjectEmailUseCase;
    this.searchAfterDateEmailService = searchAfterDateEmailService;
    this.searchBeforeDateEmailService = searchBeforeDateEmailService;
    this.searchEqualDateEmailService = searchEqualDateEmailService;
    this.searchRecipientEmailService = searchRecipientEmailService;
    this.searchSenderEmailService = searchSenderEmailService;
  }

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

  public SearchContentEmailService searchContentEmailUseCase() {
    return searchContentEmailUseCase;
  }

  public SearchSubjectEmailService searchSubjectEmailUseCase() {
    return searchSubjectEmailUseCase;
  }

  public SearchAfterDateEmailService searchAfterDateEmailService() {
    return searchAfterDateEmailService;
  }

  public SearchBeforeDateEmailService searchBeforeDateEmailService() {
    return searchBeforeDateEmailService;
  }

  public SearchEqualDateEmailService searchEqualDateEmailService() {
    return searchEqualDateEmailService;
  }

  public SearchRecipientEmailService searchRecipientEmailService() {
    return searchRecipientEmailService;
  }

  public SearchSenderEmailService searchSenderEmailService() {
    return searchSenderEmailService;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (UCCollectionSearchEmail) obj;
    return Objects.equals(this.searchContentEmailUseCase, that.searchContentEmailUseCase)
        && Objects.equals(this.searchSubjectEmailUseCase, that.searchSubjectEmailUseCase)
        && Objects.equals(this.searchAfterDateEmailService, that.searchAfterDateEmailService)
        && Objects.equals(this.searchBeforeDateEmailService, that.searchBeforeDateEmailService)
        && Objects.equals(this.searchEqualDateEmailService, that.searchEqualDateEmailService)
        && Objects.equals(this.searchRecipientEmailService, that.searchRecipientEmailService)
        && Objects.equals(this.searchSenderEmailService, that.searchSenderEmailService);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        searchContentEmailUseCase,
        searchSubjectEmailUseCase,
        searchAfterDateEmailService,
        searchBeforeDateEmailService,
        searchEqualDateEmailService,
        searchRecipientEmailService,
        searchSenderEmailService);
  }

  @Override
  public String toString() {
    return "UCCollectionSearchEmail["
        + "searchContentEmailUseCase="
        + searchContentEmailUseCase
        + ", "
        + "searchSubjectEmailUseCase="
        + searchSubjectEmailUseCase
        + ", "
        + "searchAfterDateEmailService="
        + searchAfterDateEmailService
        + ", "
        + "searchBeforeDateEmailService="
        + searchBeforeDateEmailService
        + ", "
        + "searchEqualDateEmailService="
        + searchEqualDateEmailService
        + ", "
        + "searchRecipientEmailService="
        + searchRecipientEmailService
        + ", "
        + "searchSenderEmailService="
        + searchSenderEmailService
        + ']';
  }
}
