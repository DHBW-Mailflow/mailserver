package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.time.LocalDateTime;
import java.util.List;
/**
 * @author seiferla, Jonas-Karl
 */
public record ExportableMailbox(
    String ownerAddress,
    String mailBoxType,
    List<ExportableEmail> exportableEmailList,
    LocalDateTime exportedDate) {}
