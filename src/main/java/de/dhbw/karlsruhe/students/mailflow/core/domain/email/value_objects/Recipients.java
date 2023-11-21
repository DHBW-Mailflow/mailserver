package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.List;

/**
 * Representation of e-mail recipients, consisting of the main to, the cc and
 * the bcc
 */
public record Recipients(List<Address> to, List<Address> cc, List<Address> bcc) {
}