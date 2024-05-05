package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.List;
import java.util.Objects;

/**
 * @author seiferla, Jonas-Karl
 */
public final class ExportableRecipients {
  private final List<String> bccRecipients;
  private final List<String> ccRecipients;
  private final List<String> toRecipients;

  /** */
  public ExportableRecipients(
      List<String> bccRecipients, List<String> ccRecipients, List<String> toRecipients) {
    this.bccRecipients = bccRecipients;
    this.ccRecipients = ccRecipients;
    this.toRecipients = toRecipients;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (ExportableRecipients) obj;
    return Objects.equals(this.bccRecipients, that.bccRecipients)
        && Objects.equals(this.ccRecipients, that.ccRecipients)
        && Objects.equals(this.toRecipients, that.toRecipients);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bccRecipients, ccRecipients, toRecipients);
  }

  @Override
  public String toString() {
    return "ExportableRecipients["
        + "bccRecipients="
        + bccRecipients
        + ", "
        + "ccRecipients="
        + ccRecipients
        + ", "
        + "toRecipients="
        + toRecipients
        + ']';
  }
}
