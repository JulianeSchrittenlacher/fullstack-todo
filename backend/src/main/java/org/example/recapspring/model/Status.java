package org.example.recapspring.model;

public enum Status {
    OPEN("OPEN"),
    IN_PROGRESS("IN_PROGRESS"),
    DONE("DONE");

    private final String name;
    Status(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

}
