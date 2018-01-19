package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class registerController {

    @FXML
    private Button registerButton;

    @FXML
    private Button checkAvailableButton;

    public TextField usernameField;
    public TextField eMailField;
    public PasswordField passwdField;
    public PasswordField reEnterField;
    public TextField voornaamField;
    public TextField tussenvoegselField;
    public TextField achternaamField;
    public TextField postcodeField;
    public TextField huisnummerField;

    public void checkAvailableButtonPressed() {
        // TODO check username is in database and valid
    }

    public void registerButtonPressed(ActionEvent e) {
        // TODO add the register in database function
        System.out.println(checkUserInput());

        // closes stage if account is registered
        /*
        final Node source = (Node) e.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        */
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



    // field getters
    private String getUsernameField() {
        return usernameField.getText();
    }

    private String getEMailField() {
        return eMailField.getText();
    }

    private String getpasswdField() {
        return passwdField.getText();
    }

    private String getReEnterField() {
        return reEnterField.getText();
    }

    private String getVoornaamField() {
        return voornaamField.getText();
    }

    private String getTussenvoegselField() {
        return tussenvoegselField.getText();
    }

    private String getAchternaamField() {
        return achternaamField.getText();
    }

    private String getPostcodeField() {
        return postcodeField.getText();
    }

    private String getHuisnummerField() {
        return huisnummerField.getText();
    }

    private String checkUserInput() {
        String result = "Input correct";

        // all the input fields
        final String username = getUsernameField();
        final String passwd = getpasswdField();
        final String reEnter = getReEnterField();
        final String eMail = getEMailField();
        final String voornaam = getVoornaamField();
        final String tussenvoegsel = getTussenvoegselField();
        final String achternaam = getAchternaamField();
        final String postcode = getPostcodeField();
        final String huisnummer = getHuisnummerField();

        final String eMailRegex =  "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // username: only digits or alphabetic min length 5 max 30.
        // special characters allowed: !@#$%
        if (!username.matches("^[a-zA-Z0-9!@#$%]{4,30}$")) {
            return "Incorrect username";
        }

        // checks E-mail using regex
        if (!eMail.matches(eMailRegex)) {
            // TODO check email using the web
            return "Incorrect e-mail";
        }

        // username: only digits or alphabetic min length 5 max 30
        // special characters allowed: !@#$%
        if (!passwd.matches("^[a-zA-Z0-9!@#$%]{4,30}$")) {
            return "Incorrect password";
        }

        // password is not the same as re-enter
        if (!passwd.equals(reEnter)) {
            return "Passwords doesn't match";
        }

        // voornaam: only alphabetic, lowercase or capital
        if (!voornaam.matches("[a-zA-Z /-]{3,30}")) {
            return "Invalid voornaam";
        }

        // tussenvoegsel: only alphabetic, lowercase or capital
        if (!tussenvoegsel.matches("[a-zA-Z /-]{0,10}")) {
            return "Invalid tussenvoegsel";
        }

        // achternaam: only alphabetic, lowercase or capital
        if (!achternaam.matches("[a-zA-Z /-]{3,30}")) {
            return "Invalid achternaam";
        }

        // checks postal code to dutch standards
        if (!postcode.matches("^[1-9][0-9]{3} ?(?!sa|sd|ss|SA|SD|SS)[A-Za-z]{2}$")) {
            return "invalid postcode";
        }

        // huisnummer: starts with digits and may end with alphabetic
        if (!huisnummer.matches("^\\d+/?\\d*[a-zA-Z]?(?<!/)$")) {
            return "Invalid huisnummer";
        }

        return result;
    }
}
