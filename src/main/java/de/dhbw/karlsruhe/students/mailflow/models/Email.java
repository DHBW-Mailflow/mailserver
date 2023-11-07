package de.dhbw.karlsruhe.students.mailflow.models;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class Email {
    private String subject;
    private Address sender;

    private List<Address> to;
    private List<Address> cc;
    private List<Address> bcc;

    private String content;
    private Date sentDate;
    private boolean isRead;
    private List<Header> headers;
    private Set<Attachment> attachments;

    public Email(String subject, Address sender, List<Address> to, List<Address> cc, List<Address> bcc, String content,
            Date sentDate, List<Header> headers, Set<Attachment> attachments) {
        this.subject = subject;
        this.sender = sender;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.content = content;
        this.sentDate = sentDate;
        this.headers = headers;
        this.attachments = attachments;
        this.isRead = false;
    }

    public String getSubject() {
        return subject;
    }

    public Address getSender() {
        return sender;
    }

    public List<Address> getTo() {
        return to;
    }

    public List<Address> getCc() {
        return cc;
    }

    public List<Address> getBcc() {
        return bcc;
    }

    public String getContent() {
        return content;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }
}