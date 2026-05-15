package com.fitapp.controller;

import com.fitapp.model.UserDatabaseSQLite;
import com.fitapp.navigation.Navigator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController implements Controller {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label messageLabel;
    @FXML private Button registerButton;

    private Navigator navigator;  // wird automatisch injiziert
    private final UserDatabaseSQLite userDB = new UserDatabaseSQLite();

    @Override
    public void setNavigator(Navigator navigator) {
        this.navigator = navigator;  // Navigator bekommt er vom Navigator selbst
    }

    @Override
    public void changeView(String fxmlFile) {
        navigator.changeView(fxmlFile);  // einfach weiterleiten
    }
    @FXML
    public void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirm  = confirmPasswordField.getText();

        if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            showMessage("Bitte alle Felder ausfüllen.", false); return;
        }
        if (username.length() < 3) {
            showMessage("Benutzername mind. 3 Zeichen.", false); return;
        }
        if (password.length() < 4) {
            showMessage("Passwort mind. 4 Zeichen.", false); return;
        }
        if (!password.equals(confirm)) {
            showMessage("Passwörter stimmen nicht überein!", false); return;
        }

        registerButton.setDisable(true);

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                userDB.addUser(username, password);
                return null;
            }
        };

        task.setOnSucceeded(e -> Platform.runLater(() -> {
            showMessage("✅ Account erstellt! Weiter zum Login...", true);
            registerButton.setDisable(false);
        }));

        task.setOnFailed(e -> Platform.runLater(() -> {
            String error = task.getException().getMessage();
            if (error != null && error.contains("duplicate")) {
                showMessage("❌ Benutzername bereits vergeben!", false);
            } else {
                showMessage("❌ Fehler: " + error, false);
            }
            registerButton.setDisable(false);
        }));

        new Thread(task).start();
    }

    @FXML
    public void handleBack() {
        navigator.changeView("login.fxml");
    }

    private void showMessage(String text, boolean success) {
        messageLabel.setText(text);
        messageLabel.setStyle("-fx-font-size: 12; -fx-text-fill: "
                + (success ? "#4CAF50" : "#e94560") + ";");
    }
}