package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.Objects;
import java.util.UUID;

/**
 * @author Jonas-Karl
 */
public final class EmailId {
  private final UUID id;

  /** */
  public EmailId(UUID id) {
    this.id = id;
  }

  public static EmailId createUnique() {
    return new EmailId(UUID.randomUUID());
  }

  public UUID id() {
    return id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (EmailId) obj;
    return Objects.equals(this.id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "EmailId[" + "id=" + id + ']';
  }
}
