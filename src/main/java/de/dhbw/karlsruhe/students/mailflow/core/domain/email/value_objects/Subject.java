package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.Objects;

/**
 * Representation of the e-mail subject
 *
 * @author Jonas-Karl
 */
public final class Subject {
  private final String subject;

  /** */
  public Subject(String subject) {
    this.subject = subject;
  }

  public String subject() {
    return subject;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Subject) obj;
    return Objects.equals(this.subject, that.subject);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subject);
  }

  @Override
  public String toString() {
    return "Subject[" + "subject=" + subject + ']';
  }
}
