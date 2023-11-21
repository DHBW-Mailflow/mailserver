package de.dhbw.karlsruhe.students.mailflow.core.domain.email.value_objects;

import java.util.Arrays;
import java.util.Objects;

/**
 * Representation of an e-mail attachment, consisting of the data bytes,
 * contenttype and filename
 */
public record Attachment(String filename, byte[] content, String contentType) {
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Attachment that = (Attachment) o;
        return Objects.equals(filename, that.filename) && Arrays.equals(content, that.content)
                && Objects.equals(contentType, that.contentType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(filename, contentType);
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "filename='" + filename + '\'' +
                ", content=" + Arrays.toString(content) +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}