package de.dhbw.karlsruhe.students.mailflow.unit;

import de.dhbw.karlsruhe.students.mailflow.core.domain.email.Mailbox;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.MailboxRepository;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxLoadingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.exceptions.MailboxSavingException;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import java.util.List;
import java.util.Map;

public class MockedForAllMailboxRepository implements MailboxRepository {

  private final Map<MailboxType, Mailbox> allMailboxes;

  public MockedForAllMailboxRepository(Map<MailboxType, Mailbox> allMailboxes) {
    this.allMailboxes = allMailboxes;
  }

  @Override
  public Mailbox findByAddressAndType(Address address, MailboxType type)
      throws MailboxLoadingException, MailboxSavingException {
    return allMailboxes.get(type);
  }

  @Override
  public void save(Mailbox mailbox) throws MailboxSavingException {

  }

  @Override
  public List<Mailbox> findAllOtherInboxes(Address sender)
      throws MailboxLoadingException, MailboxSavingException {
    return List.of();
  }
}
