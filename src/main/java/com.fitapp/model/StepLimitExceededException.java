package com.fitapp.model;

public class StepLimitExceededException extends IllegalStateException {

    public StepLimitExceededException() {
        super("Step goal exceeded");
    }
}
