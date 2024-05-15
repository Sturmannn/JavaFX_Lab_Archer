package org.archer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectController {
    @FXML
    TextField nicknameField;

    public GameController gameController = null;
    public Parent root;

    @FXML
    private void initialize() {
        // Загрузка main-view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/archer/main-view.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            System.err.println("Error in ConnectController.initialize: " + e.getMessage());
        }

        // Получение контроллера и установка nickname
        gameController = loader.getController();
    }

    @FXML
    public void connect() {
        if (nicknameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Nickname field is empty. Please enter a nickname.");

            alert.showAndWait();
        } else {
            gameController.setNickname(nicknameField.getText());
            // 0 - error, 1 - nickname is taken, 2 - connected, 3 - overflown
            int result = gameController.connect();
            if (result == 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Error connecting to the server. Please try again.");

                alert.showAndWait();
            } else if (result == 1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Nickname is taken. Please enter a different nickname.");

                alert.showAndWait();
            } else if (result == 3) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Server is full. Please try again later.");

                alert.showAndWait();
            } else {
                Stage stage = (Stage) nicknameField.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setResizable(false);
            }
        }
    }
}