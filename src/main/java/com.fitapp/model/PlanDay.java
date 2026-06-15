package com.fitapp.model;

import java.util.ArrayList;
import java.util.List;

public class PlanDay {

    // attributes
    private String dayName;
    private List<Exercise> exercises;

    // constructor
    public PlanDay(String dayName) {
        this.dayName = dayName;
        this.exercises = new ArrayList<>();
    }

    // getter
    public String getDayName() {
        return dayName;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    // -------------------------
    // core methods
    // -------------------------

    public void addExercise(Exercise exercise) {
        if (exercise != null) {
            exercises.add(exercise);
        }
    }

    public void removeExercise(int exerciseId) {
        exercises.removeIf(ex -> ex.getId() == exerciseId);
    }

    public Exercise getExerciseById(int id) {
        for (Exercise ex : exercises) {
            if (ex.getId() == id) {
                return ex;
            }
        }
        return null;
    }

    // -------------------------
    // calculations
    // -------------------------

    public double getTotalCalories() {
        double total = 0;

        for (Exercise ex : exercises) {
            total += ex.calcCalories();
        }

        return total;
    }

    public double getTotalDuration() {
        double total = 0;

        for (Exercise ex : exercises) {
            total += ex.getDuration();
        }

        return total;
    }

    public int getNumberOfExercises() {
        return exercises.size();
    }

    // -------------------------
    // utility
    // -------------------------

    public boolean isEmpty() {
        return exercises.isEmpty();
    }

    public void clearExercises() {
        exercises.clear();
    }

    // -------------------------
    // FIX: UI DISPLAY
    // -------------------------

    @Override
    public String toString() {
        return dayName;
    }
}