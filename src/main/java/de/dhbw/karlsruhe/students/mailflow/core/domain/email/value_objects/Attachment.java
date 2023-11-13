package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

/**
 * Representation of an e-mail attachment, consisting of the data bytes,
 * contenttype and filename
 */
public record Attachment(String filename, byte[] content, String contentType) {

}