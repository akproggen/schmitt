package com.fitapp.model;

public class NegativeStepsException extends IllegalArgumentException {

    public NegativeStepsException() {
        super("Steps cannot be negative");
    }
}
