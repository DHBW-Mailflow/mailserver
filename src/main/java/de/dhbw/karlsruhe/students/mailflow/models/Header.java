package de.dhbw.karlsruhe.students.mailflow.models;

public class Header {
    private String name;
    private String value;

    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String toString() {
        return name + ": " + value;
    }
}
