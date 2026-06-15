package com.fitapp.controller;

import com.fitapp.model.*;
import com.fitapp.model.PlanService;
import com.fitapp.navigation.Navigator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;

public class PlanController implements Controller {

    private Navigator navigator;

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;
    }

    @Override
    public void changeView(String fxmlFile) {
        navigator.changeView(fxmlFile);
    }

    // -------------------------
    // MODEL
    // -------------------------
    private PlanService planService = new PlanService();
    private Plan currentPlan;

    // -------------------------
    // FXML FIELDS
    // -------------------------
    @FXML private TextField planNameField;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;

    @FXML private ComboBox<String> dayField;

    @FXML private ComboBox<PlanDay> daySelector;
    @FXML private ComboBox<String> exerciseTypeBox;
    @FXML private ComboBox<Exercise> exerciseBox;

    @FXML private TextField durationField;
    @FXML private TextField setsField;
    @FXML private TextField repsField;

    // -------------------------
    // INIT
    // -------------------------
    @FXML
    public void initialize() {

        exerciseTypeBox.setItems(FXCollections.observableArrayList(
                "CARDIO_RUNNING",
                "CARDIO_CALISTHENICS",
                "WEIGHT"
        ));

        dayField.setItems(FXCollections.observableArrayList(
                "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag", "Sonntag"
        ));
    }

    // -------------------------
    // CREATE PLAN
    // -------------------------
    @FXML
    public void handleCreatePlan() {

        if (planNameField.getText().isEmpty()
                || startDatePicker.getValue() == null
                || endDatePicker.getValue() == null) {
            showAlert("Please fill in all fields!");
            return;
        }

        currentPlan = planService.createPlan(
                1,
                planNameField.getText(),
                java.sql.Date.valueOf(startDatePicker.getValue()),
                java.sql.Date.valueOf(endDatePicker.getValue())
        );

        showAlert("Plan created");
    }

    // -------------------------
    // ADD DAY
    // -------------------------
    @FXML
    public void handleAddDay() {

        if (currentPlan == null) {
            showAlert("Create a plan first!");
            return;
        }

        String dayName = dayField.getValue();

        if (dayName == null) {
            showAlert("Please select a valid day!");
            return;
        }

        planService.addDayToPlan(currentPlan, dayName);

        refreshDays();
        showAlert("Day added: " + dayName);
    }

    private void refreshDays() {
        daySelector.setItems(FXCollections.observableArrayList(currentPlan.getDays()));
    }

    // -------------------------
    // TYPE SELECT
    // -------------------------
    @FXML
    public void handleTypeSelect() {

        String type = exerciseTypeBox.getValue();

        if (type == null) return;

        List<Exercise> exercises = new ArrayList<>();

        if ("WEIGHT".equals(type)) {
            exercises.add(new WeightExercise(
                    1, "Bench Press", "", null,
                    "Medium", 20, 0, 80, 10, "Chest"
            ));
        }

        if ("CARDIO_RUNNING".equals(type)) {
            exercises.add(new CardioRunningExercise(
                    2, "Running", "", null,
                    "Easy", 30, 0, 5, 10, 6000
            ));
        }

        if ("CARDIO_CALISTHENICS".equals(type)) {
            exercises.add(new CardioCalisthenicsExercise(
                    3, "HIIT", "", null,
                    "Hard", 25, 0, 45, 5, 4
            ));
        }

        exerciseBox.setItems(FXCollections.observableArrayList(exercises));
    }

    // -------------------------
    // ADD EXERCISE
    // -------------------------
    @FXML
    public void handleAddExercise() {

        if (currentPlan == null) {
            showAlert("Create a plan first!");
            return;
        }

        PlanDay selectedDay = daySelector.getValue();
        Exercise selectedExercise = exerciseBox.getValue();

        if (selectedDay == null || selectedExercise == null) {
            showAlert("Select day and exercise!");
            return;
        }

        // optional duration handling
        if (!durationField.getText().isEmpty()) {
            try {
                double duration = Double.parseDouble(durationField.getText());
                selectedExercise.setDuration(duration);
            } catch (Exception e) {
                showAlert("Invalid duration!");
            }
        }

        planService.addExerciseToDay(
                currentPlan,
                selectedDay.getDayName(),
                selectedExercise
        );

        showAlert("Exercise added to " + selectedDay.getDayName());
    }

    // -------------------------
    // BACK
    // -------------------------
    @FXML
    public void handleBackToMenu(ActionEvent event) {
        changeView("mainMenu.fxml");
    }

    // -------------------------
    // UTIL
    // -------------------------
    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("FitApp");
        alert.setContentText(msg);
        alert.show();
    }
}