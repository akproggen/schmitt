package com.fitapp.controller;

import com.fitapp.model.CaloriesTracker;
import com.fitapp.model.NegativeCaloriesException;
import com.fitapp.model.CalorieLimitExceededException;
import com.fitapp.navigation.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class CaloricIntakeController implements Controller {

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
    private CaloriesTracker calTra;

    // -------------------------
    // FXML FIELDS
    // -------------------------
    @FXML
    private TextField goalField;

    @FXML
    private TextField caloriesField;

    @FXML
    private TextField remainingField;

    @FXML
    private Label caloriesOverflowLabel;

    // -------------------------
    // SET GOAL (USER INPUT)
    // -------------------------
    @FXML
    public void handleSetGoal(ActionEvent event) {
        try {
            int goal = Integer.parseInt(goalField.getText());

            calTra = new CaloriesTracker(goal);

            remainingField.textProperty().bind(
                    calTra.remainingCaloriesProperty().asString()
            );

            caloriesOverflowLabel.setVisible(false);

        } catch (NumberFormatException e) {
            caloriesOverflowLabel.setText("Please enter a valid calorie goal.");
            caloriesOverflowLabel.setVisible(true);
        }
    }

    // -------------------------
    // ADD CALORIES
    // -------------------------
    @FXML
    public void handleAddingCalories() {

        if (calTra == null) {
            caloriesOverflowLabel.setText("Please set a calorie goal first.");
            caloriesOverflowLabel.setVisible(true);
            return;
        }

        try {
            int calories = Integer.parseInt(caloriesField.getText());

            calTra.addCalories(calories);

            remainingField.textProperty().bind(
                    calTra.remainingCaloriesProperty().asString()
            );

            caloriesOverflowLabel.setVisible(false);

        } catch (NegativeCaloriesException e) {
            caloriesOverflowLabel.setText("Calories must be a positive number!");
            caloriesOverflowLabel.setVisible(true);

        } catch (CalorieLimitExceededException e) {
            caloriesOverflowLabel.setText("Exceeded daily calorie limit!");
            caloriesOverflowLabel.setVisible(true);

        } catch (NumberFormatException e) {
            caloriesOverflowLabel.setText("Please enter a valid number.");
            caloriesOverflowLabel.setVisible(true);
        }
    }

    // -------------------------
    // RESET
    // -------------------------
    @FXML
    public void handleReset(ActionEvent event) {

        if (calTra != null) {
            calTra.reset();
        }

        caloriesField.setText("");
        caloriesOverflowLabel.setVisible(false);
    }

    // -------------------------
    // BACK
    // -------------------------
    @FXML
    public void handleBackToMenu(ActionEvent event) {
        changeView("mainMenu.fxml");
    }
}