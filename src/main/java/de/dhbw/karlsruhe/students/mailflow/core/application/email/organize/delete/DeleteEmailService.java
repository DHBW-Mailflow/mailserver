package de.dhbw.karlsruhe.students.mailflow.core.application.email.organize.delete;

import com.google.gson.Gson;
import de.dhbw.karlsruhe.students.mailflow.core.application.auth.AuthSessionUseCase;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeleteEmailService implements DeleteEmailsUseCase {

  private final List<Email> deletedEmails;

  private final Gson gson;

  private final AuthSessionUseCase authSession;
  private final MailboxRepository mailboxRepository;

  public DeleteEmailService(AuthSessionUseCase authSession, MailboxRepository mailboxRepository) {
    this.authSession = authSession;
    this.mailboxRepository = mailboxRepository;
    deletedEmails = new ArrayList<>();
    gson = new Gson();
  }

  @Override
  public void delete(Email email) throws MailboxSavingException, MailboxLoadingException {

    List<Email> emailList = providePossibleEmailsToDelete();

    emailList.remove(email);
    deletedEmails.add(email);
    shiftDeletedEmail();
  }

  @Override
  public List<Email> providePossibleEmailsToDelete()
      throws MailboxSavingException, MailboxLoadingException {

    List<Email> allEmails = new ArrayList<>();
    for (MailboxType type : MailboxType.values()) {
      if (type.equals(MailboxType.DELETED)) {
        continue;
      }
      Mailbox mailbox =
          mailboxRepository.findByAddressAndType(authSession.getSessionUserAddress(), type);
      allEmails.addAll(mailbox.getEmailList());
    }
    return allEmails;
  }

  private void shiftDeletedEmail() {

    String json = gson.toJson(deletedEmails);
    try (FileWriter file = new FileWriter("deleted.json")) {
      file.write(json);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
