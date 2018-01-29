package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import database.databaseManagement;

public class persoonlijkAdviesController {

    public TextField begeleiderIDField;
    public TextField onderwerpField;
    public TextField adviesField;
    public Button goToDashboardButton;
    public Button goToHomeButton;

    @FXML
    public void adviesOpslaanButtonPressed() {
        try {
            if (getOnderwerp().equals("") || getAdvies().equals("")) {
                sceneController.showErrorPopup("Er is een fout opgetreden", "U heeft niet alle velden ingevuld.");
                return;
            }
            databaseManagement.insertPersonalAdviceRow(Integer.parseInt(getBegeleiderID()), loginDetails.getCurrentUsername(), getOnderwerp(), getAdvies());
            sceneController.showPopup("Het ingevulde advies is opgeslagen");
        } catch (Exception e) {
            sceneController.showErrorPopup("Er is een fout opgetreden", "Mogelijk ligt dit aan het ingevoerde begeleiders ID.");
            e.printStackTrace();
        }
    }

    public String getBegeleiderID() {
        return begeleiderIDField.getText();
    }

    public String getOnderwerp() {
        return onderwerpField.getText();
    }

    public String getAdvies() {
        return adviesField.getText();
    }

    @FXML
    public void goToDashboardButtonPressed(javafx.event.ActionEvent event) throws Exception {
        sceneController.changeScene(event, getClass(), "dashboardScene");
    }

    @FXML
    public void goToHomeButtonPressed(javafx.event.ActionEvent event) throws Exception{
        sceneController.changeScene(event, getClass(), "mainScene");
    }
}
