package com.fitapp.model;

import com.fitapp.model.Exercise;
import com.fitapp.model.Plan;
import com.fitapp.model.PlanDay;

import java.util.Date;

public class PlanService {

    // -------------------------
    // PLAN CREATION
    // -------------------------

    public Plan createPlan(int id, String name, Date startDate, Date endDate) {
        return new Plan(id, name, startDate, endDate, null);
    }

    // -------------------------
    // DAY MANAGEMENT
    // -------------------------

    public void addDayToPlan(Plan plan, String dayName) {
        if (plan == null || dayName == null) return;

        PlanDay day = new PlanDay(dayName);
        plan.addDay(day);
    }

    public PlanDay getDayFromPlan(Plan plan, String dayName) {
        if (plan == null) return null;
        return plan.getDay(dayName);
    }

    // -------------------------
    // EXERCISE MANAGEMENT
    // -------------------------

    public void addExerciseToDay(Plan plan, String dayName, Exercise exercise) {
        if (plan == null || exercise == null || dayName == null) return;

        PlanDay day = plan.getDay(dayName);

        if (day == null) {
            day = new PlanDay(dayName);
            plan.addDay(day);
        }

        day.addExercise(exercise);
    }

    public void removeExerciseFromDay(Plan plan, String dayName, int exerciseId) {
        if (plan == null || dayName == null) return;

        PlanDay day = plan.getDay(dayName);

        if (day != null) {
            day.removeExercise(exerciseId);
        }
    }

    // -------------------------
    // CALCULATIONS
    // -------------------------

    public double calculatePlanCalories(Plan plan) {
        if (plan == null) return 0;

        double total = 0;

        for (PlanDay day : plan.getDays()) {
            total += day.getTotalCalories();
        }

        return total;
    }

    public double calculatePlanDuration(Plan plan) {
        if (plan == null) return 0;

        double total = 0;

        for (PlanDay day : plan.getDays()) {
            total += day.getTotalDuration();
        }

        return total;
    }

    // -------------------------
    // VALIDATION (optional but useful)
    // -------------------------

    public boolean isValidPlan(Plan plan) {
        if (plan == null) return false;
        if (plan.getName() == null || plan.getName().isEmpty()) return false;
        if (plan.getStartDate() == null) return false;

        return true;
    }
}