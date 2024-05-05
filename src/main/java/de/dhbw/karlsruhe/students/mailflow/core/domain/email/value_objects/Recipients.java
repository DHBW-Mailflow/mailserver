package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.List;
import java.util.Objects;

/**
 * Representation of e-mail recipients, consisting of the main to, the cc and the bcc
 *
 * @author jens1o
 */
public final class Recipients {
  private final List<Address> to;
  private final List<Address> cc;
  private final List<Address> bcc;

  public Recipients(List<Address> to, List<Address> cc, List<Address> bcc) {
    if (to == null) {
      to = List.of();
    }
    if (cc == null) {
      cc = List.of();
    }
    if (bcc == null) {
      bcc = List.of();
    }
    this.to = to;
    this.cc = cc;
    this.bcc = bcc;
  }

  public Recipients withoutBCCRecipients() {
    return new Recipients(to, cc, List.of());
  }

  public List<Address> to() {
    return to;
  }

  public List<Address> cc() {
    return cc;
  }

  public List<Address> bcc() {
    return bcc;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (Recipients) obj;
    return Objects.equals(this.to, that.to)
        && Objects.equals(this.cc, that.cc)
        && Objects.equals(this.bcc, that.bcc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(to, cc, bcc);
  }

  @Override
  public String toString() {
    return "Recipients[" + "to=" + to + ", " + "cc=" + cc + ", " + "bcc=" + bcc + ']';
  }
}
