package edu.epam.base.entity;

public class Driver {
    private String name;
    private String surname;

    public Driver(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Driver: " + this.name + " " + this.surname;
    }
}
