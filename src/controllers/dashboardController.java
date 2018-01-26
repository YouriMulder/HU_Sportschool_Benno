package controllers;

import database.databaseManagement;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class dashboardController {

    // scene buttons and labels
    private String username = loginDetails.getCurrentUsername();
    public Button goToHomeButton;
    public Label usernameLabel;
    public ImageView profilePicture;

    // personal details box
    // personal details
    public Label personalNameLabel;
    public Label personalGenderLabel;
    public Label personalPostalCodeLabel;
    public Label personalHouseNumberLabel;

    // personal trainer details
    public Label personalTrainerNameLabel;
    public Label personalTrainerRoleLabel;
    public Label personalTrainerSLabel;

    // session box
    public ListView<String> sessiesList;


    @FXML
    public void initialize() throws Exception {
        usernameLabel.setText(username);
        loginDetails.init();

        File file = new File("src/pictures/avatar/AvatarSample.png");
        Image profileImage = new Image(file.toURI().toString());
        profilePicture.setImage(profileImage);

        // set personal details
        setPersonalDetailsBox(username);

        // puts all the sport sessions into list
        setSessionsBox(username);


    }

    @FXML
    public void goToHomeButtonPressed(javafx.event.ActionEvent event) throws Exception{
        sceneController.changeScene(event, getClass(), "mainScene");
    }

    // puts the sessions in the session box
    private void setSessionsBox(String username) throws Exception{
        ArrayList<ArrayList> sessies;
        sessies = databaseManagement.getSessiesTable(username, 0);
        ObservableList<String> items = sessiesList.getItems();
        System.out.println(sessies);
        if (!sessies.isEmpty()) {
            for (ArrayList sessie : sessies) {

                // date of the day you did sports
                String date = (String) sessie.get(1);
                date = date.substring(0, 9);

                // starting time
                String start_time = (String) sessie.get(1);
                start_time = start_time.substring(11, 19);

                // ending time
                String end_time = (String) sessie.get(2);
                end_time = end_time.substring(11, 19);

                String sessieString = String.format("%30s %30s %30s %30s", "Datum: " + date, "Begin tijd: " + start_time, "Stop tijd: " + end_time, "Totaal tijd: " + sessie.get(3));
                items.add(sessieString);

            }
        } else {
            items.add("Je hebt afgelopen tijd niet gesport bij onze sportschool.");
        }
    }

    public void setPersonalDetailsBox(String username) throws Exception {
        ArrayList<String> klantenTable = new ArrayList();
        klantenTable = databaseManagement.getKlantenTable(username, 0, 0, 0);

        String klant_id = klantenTable.get(0);
        String voornaam = klantenTable.get(1);
        String tussenvoegsel = klantenTable.get(2);
        String achternaam = klantenTable.get(3);
        String geslacht = klantenTable.get(4);
        String postcode = klantenTable.get(5);
        String huisnummer = klantenTable.get(6);
        String account_id = klantenTable.get(7);
        String abonnement_id = klantenTable.get(8);
        String begeleider_id = klantenTable.get(9);

        // personal details
        // checks if user has a infix
        if(tussenvoegsel == null) {
            personalNameLabel.setText(voornaam + " " + achternaam);
        }
        else {
            personalNameLabel.setText(voornaam + " " + tussenvoegsel + " " + achternaam);
        }

        personalGenderLabel.setText(geslacht);
        personalPostalCodeLabel.setText(postcode);
        personalHouseNumberLabel.setText(huisnummer);

        // personal trainer details
    }

}
