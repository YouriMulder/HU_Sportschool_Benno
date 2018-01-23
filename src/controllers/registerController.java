package controllers;

import database.databaseManagement;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Paths;

public class registerController {

    // all the buttons

    // info buttons
    public Button usernameInfo;
    public Button eMailInfo;
    public Button passwdInfo;
    public Button reEnterInfo;
    public Button voorwaardenInfo;


    public Button registerButton;
    public Button checkAvailableButton;

    // all the input fields
    public TextField usernameField;
    public TextField eMailField;
    public PasswordField passwdField;
    public PasswordField reEnterField;
    public TextField voornaamField;
    public TextField tussenvoegselField;
    public TextField achternaamField;
    public TextField postcodeField;
    public TextField huisnummerField;
    public RadioButton termsButton;
    public ChoiceBox genderBox;

    public void checkAvailableButtonPressed() throws Exception {
        String username = usernameField.getText();

        Boolean usernameValid = checkUsernameValid(username);
        if (usernameValid) {
            Boolean usernameExists = databaseManagement.checkUsernameUsed(username);
            if (usernameExists) {
                // username exists
                sceneController.showPopup("De ingevoerde username is al in gebruik.");
            } else {
                // username doesn't exists
                sceneController.showPopup("De ingevoerde username is nog niet gebruikt.");
            }
        } else {
            // username is not valid
            sceneController.showPopup("Username voldoet niet aan de eisen.\nDruk op de ? om te kunnen zien wat de eisen zijn.");
        }
    }

    public void registerButtonPressed(ActionEvent a) throws Exception {
        // TODO add the register in database function
        String checkUserInput = checkUserInput();
        if (!checkUserInput.equals("Input correct")) {
            sceneController.showErrorPopup("An error occurred while trying to register", checkUserInput);
        } else {
            // all the input fields
            final String username = getUsernameField();
            final String passwd = getpasswdField();
            final String eMail = getEMailField();
            final String voornaam = getVoornaamField();
            final String tussenvoegsel = getTussenvoegselField();
            final String achternaam = getAchternaamField();
            final String postcode = getPostcodeField();
            final String huisnummer = getHuisnummerField();
            final String geslacht = getGenderBox();

            // TODO create account in database
            databaseManagement.registerAccount(username, passwd, eMail, termsButton.isSelected());
            int account_id = databaseManagement.getAccountID(username);
            databaseManagement.registerCustomer(voornaam, tussenvoegsel, achternaam, geslacht, postcode, huisnummer, account_id);
            databaseManagement.updateAccount_klantID(account_id);
            // closes stage if account is registered
            sceneController.showPopup("Account created successfully");
            final Node source = (Node) a.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
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

    // information buttons
    // show terms and conditions
    public void usernameInfoPressed() throws Exception {
        String content;
        // the full path is needed. Otherwise the program won't run
        content = new String(Files.readAllBytes(Paths.get("src/registerInfo/usernameInfo.txt")));
        sceneController.showPopup(content);
    }

    public void eMailInfoPressed() throws Exception {
        String content;
        // the full path is needed. Otherwise the program won't run
        content = new String(Files.readAllBytes(Paths.get("src/registerInfo/eMailInfo.txt")));
        sceneController.showPopup(content);
    }

    public void passwdInfoPressed() throws Exception {
        String content;
        // the full path is needed. Otherwise the program won't run
        content = new String(Files.readAllBytes(Paths.get("src/registerInfo/passwdInfo.txt")));
        sceneController.showPopup(content);
    }

    public void reEnterInfoPressed() throws Exception {
        String content;
        // the full path is needed. Otherwise the program won't run
        content = new String(Files.readAllBytes(Paths.get("src/registerInfo/reEnterInfo.txt")));
        sceneController.showPopup(content);
    }

    public void voorwaardenInfoPressed() throws Exception {
        String content;
        // the full path is needed. Otherwise the program won't run
        content = new String(Files.readAllBytes(Paths.get("src/terms/terms.txt")));
        sceneController.showPopup(content);
    }

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

    private String getGenderBox() {
        return genderBox.getValue().toString();
    }

    private boolean checkUsernameValid(String usernameInput) {
        return usernameInput.matches("^[a-zA-Z0-9!@#$%-_]{4,30}$");
    }

    private String checkUserInput() throws Exception {
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
        if (!username.matches("^[a-zA-Z0-9!@#$%-_]{4,30}$")) {
            return "Incorrect username";
        }

        // checks if username is available
        if (databaseManagement.checkUsernameUsed(username)) {
            return "Username is al in gebruik";
        }

        // checks E-mail using regex
        if (!eMail.matches(eMailRegex)) {
            // TODO check email using the web
            return "Incorrect e-mail";
        }

        // username: only digits or alphabetic min length 5 max 30
        // special characters allowed: !@#$%
        if (!passwd.matches("^[a-zA-Z0-9!@#$%-_]{4,30}$")) {
            return "Fout in password veld";
        }

        // password is not the same as re-enter
        if (!passwd.equals(reEnter)) {
            return "Password zijn niet hetzelfde";
        }

        // voornaam: only alphabetic, lowercase or capital
        if (!voornaam.matches("[a-zA-Z /-]{3,30}")) {
            return "Fout in voornaam veld";
        }

        // tussenvoegsel: only alphabetic, lowercase or capital
        if (!tussenvoegsel.matches("[a-zA-Z /-]{0,10}")) {
            return "Fout in tussenvoegsel veld";
        }

        // achternaam: only alphabetic, lowercase or capital
        if (!achternaam.matches("[a-zA-Z /-]{3,30}")) {
            return "Fout in achternaam veld";
        }

        // checks postal code to dutch standards
        if (!postcode.matches("^[1-9][0-9]{3} ?(?!sa|sd|ss|SA|SD|SS)[A-Za-z]{2}$")) {
            return "Fout in postcode veld";
        }

        // huisnummer: starts with digits and may end with alphabetic
        if (!huisnummer.matches("^\\d+/?\\d*[a-zA-Z]?(?<!/)$")) {
            return "Fout in huisnummer veld";
        }

        if (!termsButton.isSelected()) {
            return "Ga akkoord met de voorwaarden als je een account wil aanmaken";
        }

        return result;
    }
}
