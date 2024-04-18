package de.dhbw.karlsruhe.students.mailflow.core.application.email.provide;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Email;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.ArrayList;
import java.util.List;
/**
 * @author seiferla , Jonas-Karl

 */
public class ProvideAllEmailService implements ProvideEmailsUseCase {

  private final MailboxRepository mailboxRepository;

  public ProvideAllEmailService(MailboxRepository mailboxRepository) {
    this.mailboxRepository = mailboxRepository;
  }

  @Override
  public List<Email> provideEmails(Address sessionUserAddress)
      throws MailboxSavingException, MailboxLoadingException {
    List<Email> emailList = new ArrayList<>();

    for (MailboxType mailboxType : MailboxType.values()) {
      emailList.addAll(
          mailboxRepository.findByAddressAndType(sessionUserAddress, mailboxType).getEmailList());
    }
    return emailList;
  }

  @Override
  public String getMailboxName() {
    return "All";
  }
}
