package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author seiferla, Jonas-Karl
 */
public record ExportableEmail(
    String subject,
    String content,
    String senderAddress,
    ExportableRecipients recipient,
    LocalDateTime sendDate,
    boolean isRead,
    Map<String, String> header) {}
