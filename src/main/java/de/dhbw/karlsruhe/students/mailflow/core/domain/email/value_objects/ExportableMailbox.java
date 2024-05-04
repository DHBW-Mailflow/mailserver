package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.time.LocalDateTime;
import java.util.List;

public record ExportableMailbox(
    String ownerAddress,
    String mailBoxType,
    List<ExportableEmails> exportableEmailsList,
    LocalDateTime exportedDate) {}
