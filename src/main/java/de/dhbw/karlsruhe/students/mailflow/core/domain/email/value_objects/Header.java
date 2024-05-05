package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.Objects;

/**
 * Representation of a custom e-mail header, consisting of a name and a value
 *
 * @author jens1o
 */
public final class Header {
  private final String name;
  private final String value;

  /** */
  public Header(String name, String value) {
    this.name = name;
    this.value = value;
  }

  @Override
  public String toString() {
    return name + ": " + value;
  }

  public String name() {
    return name;
  }

  public String value() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Header) obj;
    return Objects.equals(this.name, that.name) && Objects.equals(this.value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, value);
  }
}
