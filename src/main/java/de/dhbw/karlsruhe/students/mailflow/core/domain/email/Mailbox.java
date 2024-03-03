package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.AggregateRoot;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.mailbox.value_object.MailboxId;

public class Mailbox extends AggregateRoot<MailboxId> {

  /**
   * The address of this mailbox, e.g. `manuel@mueller.de` in
   * {@literal Manuel Müller <manuel@mueller.de>}
   */
  private Address address;

  private List<Email> emails;

  private Mailbox(MailboxId id, Address address, List<Email> emails) {
    super(id);
    this.emails = emails;
    this.address = address;
  }

  public static Mailbox create(Address address, List<Email> emails) {
    return new Mailbox(MailboxId.createUnique(), address, emails);
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
}
