package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;

public class MockedMailboxRepository implements MailboxRepository {

  private final Mailbox mailbox;

  public MockedMailboxRepository(Mailbox mailbox) {
    this.mailbox = mailbox;
  }


  @Override
  public Mailbox findByAddressAndType(Address address, MailboxType type)
      throws MailboxLoadingException, MailboxSavingException {
    return mailbox;
  }

  @Override
  public void save(Mailbox mailbox) throws MailboxSavingException {}

  @Override
  public List<Mailbox> findAllOtherInboxes(Address sender)
      throws MailboxLoadingException, MailboxSavingException {
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }
}
