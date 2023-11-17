package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;
/**
 * 
 */
public record EmailMetadata(Subject subject, Address sender, Recipients recipients, SentDate sentDate) {
}