package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.Objects;

/**
 * Representation of an e-mail address, consisting of a localPart and a domain, separated by an `@`
 * sign.
 *
 * @author jens1o
 */
public final class Address {
  private final String localPart;
  private final String domain;

  /** */
  public Address(String localPart, String domain) {
    this.localPart = localPart;
    this.domain = domain;
  }

  /**
   * @param emailString email to parse
   * @return the address object of the given email
   * @throws IllegalArgumentException when no @-sign was provided
   */
  public static Address from(String emailString) {
    int index = emailString.lastIndexOf("@");
    if (index == -1) {
      throw new IllegalArgumentException("No \"@\" was provided in %s".formatted(emailString));
    }
    return new Address(
        emailString.substring(0, index).toLowerCase(),
        emailString.substring(index + 1).toLowerCase());
  }

  @Override
  public String toString() {
    return localPart + "@" + domain;
  }

  public String localPart() {
    return localPart;
  }

  public String domain() {
    return domain;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Address) obj;
    return Objects.equals(this.localPart, that.localPart)
        && Objects.equals(this.domain, that.domain);
  }

  @Override
  public int hashCode() {
    return Objects.hash(localPart, domain);
  }
}
