package com.fitapp.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class StepTracker {

    private IntegerProperty dailyGoal;
    private IntegerProperty currentSteps;
    private IntegerProperty remainingSteps;

    public StepTracker(int dailyGoal) {
        this.dailyGoal = new SimpleIntegerProperty(dailyGoal);
        this.currentSteps = new SimpleIntegerProperty(0);
        this.remainingSteps = new SimpleIntegerProperty(dailyGoal);
    }

    public void addSteps(int steps)

            throws NegativeStepsException, StepLimitExceededException {

        if (steps < 0) {
            throw new NegativeStepsException();
        }

        if (currentSteps.get() + steps > dailyGoal.get()) {
            throw new StepLimitExceededException();
        }

        currentSteps.set(currentSteps.get() + steps);
        remainingSteps.set(dailyGoal.get() - currentSteps.get());
    }

    public void reset() {
        currentSteps.set(0);
        remainingSteps.set(dailyGoal.get());
    }

    // Getter für Binding
    public IntegerProperty remainingStepsProperty() {
        return remainingSteps;
    }

    public IntegerProperty getDailyGoal() {
        return dailyGoal;
    }

    public IntegerProperty currentStepsProperty() {
        return currentSteps;
    }



}