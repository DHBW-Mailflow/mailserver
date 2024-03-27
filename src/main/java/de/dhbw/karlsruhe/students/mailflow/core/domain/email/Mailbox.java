package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.AggregateRoot;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.MailboxId;
import java.util.List;

/**
 * @author jens1o, Jonas-Karl
 */
public final class Mailbox extends AggregateRoot<MailboxId> {

  /**
   * The address of this mailbox, e.g. `manuel@mueller.de` in {@literal Manuel Müller
   * <manuel@mueller.de>}
   */
  private final Address address;

  private List<Email> emails;
  private final MailboxType type;

  private Mailbox(MailboxId id, Address address, List<Email> emails, MailboxType type) {
    super(id);
    if (type == null) {
      type = MailboxType.READ;
    }
    this.emails = emails;
    this.address = address;
    this.type = type;
  }

  public static Mailbox create(Address address, List<Email> emails, MailboxType type) {
    return new Mailbox(MailboxId.createUnique(), address, emails, type);
  }

  public List<Email> getEmails() {
    return emails;
  }

  @Override
  public String toString() {

    return this.address.toString();
  }

  @Override
  public boolean equals(Object other) {
    return super.equals(other);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  public MailboxType getType() {
    return type;
  }

  public Address getOwner() {
    return address;
  }
}
