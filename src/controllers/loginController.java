package controllers;

import database.databaseManagement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class loginController {
    public TextField usernameField;
    public PasswordField passwdField;

    @FXML
    public void loginButtonPressed() {
        String usernameInput = usernameField.getText().toLowerCase();
        String passwdInput = passwdField.getText().toLowerCase();

        // checks if user input can be found in the database
        String accountCorrect = databaseManagement.checkLoginData(usernameInput, passwdInput);

        if (accountCorrect.equals("Login details correct")) {
            System.out.println("Logged in as " + usernameInput + "");
            // TODO go to the next page
        }
        else {
            sceneController.showErrorPopup("An error occurred when logging in" ,accountCorrect);
        }
    }

    @FXML
    public void goToHomeButtonPressed(javafx.event.ActionEvent event) throws Exception {
        sceneController.changeScene(event, getClass(), "mainScene");
    }

    @FXML
    public void goToRegisterButtonPressed(javafx.event.ActionEvent event) throws Exception{
        sceneController.createRegisterStage(event, getClass());
    }

}
