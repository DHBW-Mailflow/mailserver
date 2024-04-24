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

    if (emailList.contains(email)) {
      emailList.remove(email);
      deletedEmails.add(email);
      shiftDeletedEmail();
    }
  }

  @Override
  public List<Email> providePossibleEmailsToDelete()
      throws MailboxSavingException, MailboxLoadingException {

    Mailbox mailbox =
        mailboxRepository.findByAddressAndType(
            authSession.getSessionUserAddress(), MailboxType.INBOX);
    return mailbox.getEmailList();
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
