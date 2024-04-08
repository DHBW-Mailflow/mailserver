package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.AggregateRoot;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.MailboxId;
import java.util.List;
import java.util.Map;

/**
 * @author jens1o, Jonas-Karl
 */
public final class Mailbox extends AggregateRoot<MailboxId> {

  /**
   * The address of this mailbox, e.g. `manuel@mueller.de` in {@literal Manuel Müller
   * <manuel@mueller.de>}
   */
  private final Address address;

  private final Map<Email, List<Label>> emails;
  private final MailboxType type;

  private Mailbox(MailboxId id, Address address, Map<Email, List<Label>> emails, MailboxType type) {
    super(id);
    if (type == null) {
      type = MailboxType.INBOX;
    }
    this.emails = emails;
    this.address = address;
    this.type = type;
  }

  public static Mailbox create(Address address, Map<Email, List<Label>> emails, MailboxType type) {
    return new Mailbox(MailboxId.createUnique(), address, emails, type);
  }

  public List<Email> getEmailList() {
    return emails.keySet().stream().toList();
  }

  public Map<Email, List<Label>> getEmailsWithLabels() {
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
