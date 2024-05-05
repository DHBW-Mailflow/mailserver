package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Jonas-Karl
 */
public final class MailboxId {
  private final UUID id;

  /** */
  public MailboxId(UUID id) {
    this.id = id;
  }

  public static MailboxId createUnique() {
    return new MailboxId(UUID.randomUUID());
  }

  public UUID id() {
    return id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (MailboxId) obj;
    return Objects.equals(this.id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "MailboxId[" + "id=" + id + ']';
  }
}
