package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.List;

/**
 * Representation of e-mail recipients, consisting of the main to, the cc and the bcc
 *
 * @author jens1o
 */
public record Recipients(List<Address> to, List<Address> cc, List<Address> bcc) {
  public Recipients withoutBCCRecipients() {
    return new Recipients(to, cc, List.of());
  }

  public Recipients {
    if (to == null) {
      to = List.of();
    }
    if (cc == null) {
      cc = List.of();
    }
    if (bcc == null) {
      bcc = List.of();
    }
  }
}
