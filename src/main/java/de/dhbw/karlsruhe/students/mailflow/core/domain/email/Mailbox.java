package de.dhbw.karlsruhe.students.mailflow.core.domain.email;

import de.dhbw.karlsruhe.students.mailflow.core.domain.common.models.AggregateRoot;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.Label;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.enums.MailboxType;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.Address;
import de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects.MailboxId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @author jens1o, Jonas-Karl
 */
public final class Mailbox extends AggregateRoot<MailboxId> {

  /**
   * The address of this mailbox, e.g. `manuel@mueller.de` in {@literal Manuel Müller
   * <manuel@mueller.de>}
   */
  private final Address address;

  private final Map<Email, Set<Label>> emails;
  private final MailboxType type;

  private Mailbox(MailboxId id, Address address, Map<Email, Set<Label>> emails, MailboxType type) {
    super(id);
    if (type == null) {
      type = MailboxType.INBOX;
    }
    this.emails = emails;
    this.address = address;
    this.type = type;
  }

  public static Mailbox create(Address address, Map<Email, Set<Label>> emails, MailboxType type) {
    return new Mailbox(MailboxId.createUnique(), address, emails, type);
  }

  public List<Email> getEmailList() {
    return emails.keySet().stream().toList();
  }

  /**
   * @deprecated, use {@code #markWithLabel} instead
   *
   * @param email
   * @param isUnread
   */
  @Deprecated(forRemoval = true)
  public void deliverEmail(Email email, boolean isUnread) {
    this.emails.put(email, Set.of(isUnread ? Label.UNREAD : Label.READ));
  }

  /**
   * Deletes this e-mail from this mailbox. This action is idempotent.
   *
   * @param email
   * @return {@link Optional#empty()} when the e-mail was not in this inbox, otherwise returns the
   *         Set of labels the email had assigned
   */
  public Optional<Set<Label>> deleteEmail(Email email) {
    Set<Label> labels = this.emails.remove(email);

    if (labels == null) {
      return Optional.empty();
    }

    return Optional.of(labels);
  }

  /**
   * @param labels
   * @return returns all emails that have at least one of the supplied labels
   */
  public List<Email> getEmailsWithLabel(Label... labels) {
    return emails.entrySet().stream().filter(entry -> {
      for (Label label : labels) {
        if (entry.getValue().contains(label)) {
          return true;
        }
      }

      return false;
    }).map(Map.Entry::getKey).toList();
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

  public void markWithLabel(Email email, Label... label) {
    emails.put(email, Set.of(label));
  }


  /**
   * Deletes this e-mail from this mailbox. This action is idempotent.
   *
   * @param email
   * @return {@link Optional#empty()} when the e-mail was not in this inbox, otherwise returns the
   *         Set
   */
  public Optional<Set<Label>> deleteEmail(Email email) {
    Set<Label> labels = this.emails.remove(email);

    if (labels == null) {
      return Optional.empty();
    }

    return Optional.of(labels);
  }
}
