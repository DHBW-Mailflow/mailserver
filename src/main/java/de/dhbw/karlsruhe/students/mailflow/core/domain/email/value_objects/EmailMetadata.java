package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;
/**
 * Representation of constant e-mail, which will exist with the creation of an e-mail
 */
public record EmailMetadata(Subject subject, Address sender, Recipients recipients, SentDate sentDate) {
}