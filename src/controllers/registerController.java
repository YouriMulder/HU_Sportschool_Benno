package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class registerController {

    @FXML
    private Button registerButton;

    @FXML
    private Button checkAvailableButton;

    public void checkAvailableButtonPressed() {

    }

    public void registerButtonPressed(ActionEvent e) {
        // TODO add the register in database function

        // closes stage if account is registered
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


    /*
    // TODO move to the register page
    public void registerButtonPressed() {
        // TODO make register function in the database
        // TODO make register message show up as popup
        // TODO passwords are in database without capitals
        String usernameInput = usernameField.getText().toLowerCase();
        String passwdInput = passwdField.getText().toLowerCase();

        // registers account and returns a message based on the error or success
        String registerMessage = databaseManagement.registerAccount(usernameInput, passwdInput);
        if (registerMessage.equals("Account created successfully")) {
            emptyLoginFields();
            showPopup("Your account is successfully created");
        } else {
            emptyPasswdField();
            showErrorPopup("OOPS, something went wrong", registerMessage);
        }

        System.out.println(registerMessage);
    }

    private void emptyLoginFields() {
        usernameField.setText(null);
        passwdField.setText(null);
    }

    private void emptyPasswdField() {
        passwdField.setText(null);
    }
    */
}
