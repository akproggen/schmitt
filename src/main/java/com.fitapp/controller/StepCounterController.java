package com.fitapp.controller;

import com.fitapp.model.StepTracker;
import com.fitapp.model.NegativeStepsException;
import com.fitapp.model.StepLimitExceededException;
import com.fitapp.navigation.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class StepCounterController implements Controller {

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
    private StepTracker stepTracker;

    // -------------------------
    // FXML FIELDS
    // -------------------------
    @FXML
    private TextField goalField;

    @FXML
    private TextField stepsField;

    @FXML
    private TextField remainingField;

    @FXML
    private Label stepOverflowLabel;

    // -------------------------
    // SET GOAL (USER INPUT)
    // -------------------------
    @FXML
    public void handleSetGoal(ActionEvent event) {
        try {
            int goal = Integer.parseInt(goalField.getText());

            stepTracker = new StepTracker(goal);

            remainingField.textProperty().bind(
                    stepTracker.remainingStepsProperty().asString()
            );

            stepOverflowLabel.setVisible(false);

        } catch (NumberFormatException e) {
            stepOverflowLabel.setText("Please enter a valid step goal.");
            stepOverflowLabel.setVisible(true);
        }
    }

    // -------------------------
    // ADD STEPS
    // -------------------------
    @FXML
    public void handleAddingSteps() {
        if (stepTracker == null) {
            stepOverflowLabel.setText("Please set a goal first.");
            stepOverflowLabel.setVisible(true);
            return;
        }

        try {
            int steps = Integer.parseInt(stepsField.getText());

            stepTracker.addSteps(steps);

            remainingField.textProperty().bind(
                    stepTracker.remainingStepsProperty().asString()
            );

            stepOverflowLabel.setVisible(false);

        } catch (NegativeStepsException e) {
            stepOverflowLabel.setText("Steps must be a positive number!");
            stepOverflowLabel.setVisible(true);

        } catch (StepLimitExceededException e) {
            stepOverflowLabel.setText("Exceeded daily step goal!");
            stepOverflowLabel.setVisible(true);

        } catch (NumberFormatException e) {
            stepOverflowLabel.setText("Please enter a valid number.");
            stepOverflowLabel.setVisible(true);
        }
    }

    // -------------------------
    // RESET
    // -------------------------
    @FXML
    public void handleReset(ActionEvent event) {
        if (stepTracker != null) {
            stepTracker.reset();
        }

        stepsField.setText("");
        stepOverflowLabel.setVisible(false);
    }

    // -------------------------
    // BACK
    // -------------------------
    @FXML
    public void handleBackToMenu(ActionEvent event) {
        changeView("mainMenu.fxml");
    }
}