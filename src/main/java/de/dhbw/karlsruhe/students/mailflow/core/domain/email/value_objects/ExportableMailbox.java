package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author seiferla, Jonas-Karl
 */
public final class ExportableMailbox {
  private final String ownerAddress;
  private final String mailBoxType;
  private final List<ExportableEmail> exportableEmailList;
  private final LocalDateTime exportedDate;

  /** */
  public ExportableMailbox(
      String ownerAddress,
      String mailBoxType,
      List<ExportableEmail> exportableEmailList,
      LocalDateTime exportedDate) {
    this.ownerAddress = ownerAddress;
    this.mailBoxType = mailBoxType;
    this.exportableEmailList = exportableEmailList;
    this.exportedDate = exportedDate;
  }

  public String ownerAddress() {
    return ownerAddress;
  }

  public String mailBoxType() {
    return mailBoxType;
  }

  public LocalDateTime exportedDate() {
    return exportedDate;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (ExportableMailbox) obj;
    return Objects.equals(this.ownerAddress, that.ownerAddress)
        && Objects.equals(this.mailBoxType, that.mailBoxType)
        && Objects.equals(this.exportableEmailList, that.exportableEmailList)
        && Objects.equals(this.exportedDate, that.exportedDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ownerAddress, mailBoxType, exportableEmailList, exportedDate);
  }

  @Override
  public String toString() {
    return "ExportableMailbox["
        + "ownerAddress="
        + ownerAddress
        + ", "
        + "mailBoxType="
        + mailBoxType
        + ", "
        + "exportableEmailList="
        + exportableEmailList
        + ", "
        + "exportedDate="
        + exportedDate
        + ']';
  }
}
