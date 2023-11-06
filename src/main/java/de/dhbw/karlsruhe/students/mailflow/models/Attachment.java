package de.dhbw.karlsruhe.students.mailflow.models;

public class Attachment {
    private String filename;
    private byte[] content;
    private String contentType;

    public Attachment(String filename, byte[] content, String contentType) {
        this.filename = filename;
        this.content = content;
        this.contentType = contentType;
    }

    public String getFilename() {
        return filename;
    }

    public byte[] getContent() {
        return content;
    }

    public String getContentType() {
        return contentType;
    }
}
