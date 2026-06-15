package com.fitapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Plan {

    // attributes
    private int id;
    private String name;
    private Date startDate;
    private Date endDate;

    // NEW: days instead of flat exercise list
    private List<PlanDay> days;

    // constructor
    public Plan(int id, String name, Date startDate, Date endDate, List<PlanDay> days) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = (days != null) ? days : new ArrayList<>();
    }

    // getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<PlanDay> getDays() {
        return days;
    }

    // -------------------------
    // core plan methods
    // -------------------------

    public void addDay(PlanDay day) {
        if (day != null) {
            days.add(day);
        }
    }

    public PlanDay getDay(String dayName) {
        for (PlanDay day : days) {
            if (day.getDayName().equalsIgnoreCase(dayName)) {
                return day;
            }
        }
        return null;
    }

    // -------------------------
    // optional calculations
    // -------------------------

    public double getTotalCalories() {
        double total = 0;

        for (PlanDay day : days) {
            for (Exercise ex : day.getExercises()) {
                total += ex.calcCalories();
            }
        }

        return total;
    }

    public double getTotalDuration() {
        double total = 0;

        for (PlanDay day : days) {
            for (Exercise ex : day.getExercises()) {
                total += ex.getDuration();
            }
        }

        return total;
    }

    // -------------------------
    // utility
    // -------------------------

    public int getNumberOfDays() {
        return days.size();
    }

    public void removeDay(String dayName) {
        days.removeIf(day -> day.getDayName().equalsIgnoreCase(dayName));
    }
}