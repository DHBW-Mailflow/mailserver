package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.List;
/**
 * @author seiferla, Jonas-Karl
 */
public record ExportableRecipients(
    List<String> bccRecipients, List<String> ccRecipients, List<String> toRecipients) {}
