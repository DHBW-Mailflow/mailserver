package de.dhbw.karlsruhe.students.mailflow.models;

import java.util.HashSet;

import de.dhbw.karlsruhe.students.mailflow.helpers.TimingSafe;

public class Mailbox {

    /**
     * The human readable name of this mailbox, e.g. `manuel@mueller.de` in
     * {@literal Manuel Müller <manuel@mueller.de>}
     */
    private Address address;
    /**
     * The human readable name of this mailbox, e.g. `Manuel Müller` in
     * {@literal Manuel Müller <manuel@mueller.de>}
     */
    private String name;

    private String username;
    private String password;
    private HashSet<Email> emails;

    public Mailbox(String username, String password) {
        this.username = username;
        this.password = password;
        this.emails = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public HashSet<Email> getEmails() {
        return emails;
    }

    public String toString() {
        if (this.name.length() == 0) {
            return this.address.toString();
        }

        return this.name + " <" + this.address.toString() + ">";
    }
}
