package controllers;

import database.databaseManagement;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
    public Label personalTNameLabel;
    public Label personalTRoleLabel;
    public Label personalTSLabel;
    public Label personalTIdLabel;

    // session box
    public ListView<String> sessiesList;

    // personal trainers box
    public ListView<String> begeleidersList;
    public Button veranderBegeleiderButton;

    @FXML
    public void initialize() throws Exception {
        usernameLabel.setText(username);
        loginDetails.init();

        File file = new File("src/pictures/avatar/AvatarSample.png");
        Image profileImage = new Image(file.toURI().toString());
        profilePicture.setImage(profileImage);

        // set personal details
        setPersonalDetailsBox(username);

        // puts all the sport sessions into the sessions box
        setSessionsBox(username);

        // puts all the personal trainers into the personal trainers box
        setPersonalTrainersBox();


    }

    @FXML
    public void goToHomeButtonPressed(javafx.event.ActionEvent event) throws Exception{
        sceneController.changeScene(event, getClass(), "mainScene");
    }

    // puts the sessions in the session box
    private void setSessionsBox(String username) throws Exception{
        ArrayList<ArrayList> sessies;
        sessies = databaseManagement.getSessiesRows(username, 0);
        ObservableList<String> items = sessiesList.getItems();
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

                String sessieString = String.format("%-20s %-25s %-25s %-25s", "Datum: " + date, "Begin tijd: " + start_time, "Stop tijd: " + end_time, "Totaal tijd: " + sessie.get(3));
                items.add(sessieString);

            }
        } else {
            items.add("Je hebt afgelopen tijd niet gesport bij onze sportschool.");
        }
    }

    public void setPersonalDetailsBox(String username) throws Exception {
        ArrayList<String> klantenTable = new ArrayList();
        klantenTable = databaseManagement.getKlantenRow(username, 0, 0, 0);

        // If you are a customer set all the values
        if (!klantenTable.isEmpty()) {
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
            if (tussenvoegsel == null) {
                personalNameLabel.setText(voornaam + " " + achternaam);
            } else {
                personalNameLabel.setText(voornaam + " " + tussenvoegsel + " " + achternaam);
            }

            personalGenderLabel.setText(geslacht);
            personalPostalCodeLabel.setText(postcode);
            personalHouseNumberLabel.setText(huisnummer);


            ArrayList<String> begeleidersTable = new ArrayList();

            begeleidersTable = databaseManagement.getBegeleidersRow(begeleider_id);

            // if there is a personal trainer assigned to your name set all the values
            if (!begeleidersTable.isEmpty()) {
                String personalTBegeleider_id = begeleidersTable.get(0);
                String personalTVoornaam = begeleidersTable.get(1);
                String personalTTussenvoegsel = begeleidersTable.get(2);
                String personalTAchternaam = begeleidersTable.get(3);
                String personalTGeslacht = begeleidersTable.get(4);
                String personalTRol = begeleidersTable.get(5);
                String personalTSpecialisatie = begeleidersTable.get(6);
                String personalTContractStart = begeleidersTable.get(7);
                String PersonalTConcractEind = begeleidersTable.get(8);

                // personal trainer details
                // check if there is an infix
                if (tussenvoegsel == null) {
                    personalTNameLabel.setText(personalTVoornaam + " " + personalTAchternaam);
                } else {
                    personalTNameLabel.setText(personalTVoornaam + " " + personalTTussenvoegsel + " " + personalTAchternaam);
                }

                personalTRoleLabel.setText(personalTRol);
                personalTSLabel.setText(personalTSpecialisatie);
                personalTIdLabel.setText(personalTBegeleider_id);
            }
        }
    }

    // puts the sessions in the session box
    private void setPersonalTrainersBox() throws Exception {
        ArrayList<ArrayList> personalTrainers;
        personalTrainers = databaseManagement.getBegeleidersTable();
        ObservableList<String> items = begeleidersList.getItems();

        if (!personalTrainers.isEmpty()) {
            for (ArrayList personalTrainer : personalTrainers) {
                String personalTBegeleider_id = (String) personalTrainer.get(0);
                String personalTVoornaam = (String) personalTrainer.get(1);
                String personalTTussenvoegsel = (String) personalTrainer.get(2);
                String personalTAchternaam = (String) personalTrainer.get(3);
                String personalTGeslacht = (String) personalTrainer.get(4);
                String personalTRol = (String) personalTrainer.get(5);
                String personalTSpecialisatie = (String) personalTrainer.get(6);
                String personalTContractStart = (String) personalTrainer.get(7);
                String PersonalTConcractEind = (String) personalTrainer.get(8);


                String personalTrainerName;
                // check if there is an infix
                if (personalTrainer.get(2) == null) {
                    personalTrainerName = personalTVoornaam + " " + personalTAchternaam;
                } else {
                    personalTrainerName = personalTVoornaam + " " + personalTTussenvoegsel + " " + personalTAchternaam;
                }



                String personalTrainerString = String.format("%-2s %-26s %-16s %-16s %-16s", personalTBegeleider_id, "Naam: " + personalTrainerName, "Geslacht: " + personalTGeslacht, "Rol: " + personalTRol, "Specialisatie: " + personalTSpecialisatie);
                items.add(personalTrainerString);

            }
        } else {
            items.add("Er zijn geen begeleiders beschikbaar.");
        }
    }

    // change your personal trainer
    public void veranderBegeleiderButtonClicked() throws Exception {
        String chosenPersonalTrainer = begeleidersList.getSelectionModel().getSelectedItems().toString();

        String[] chosenPersonalTrainerList = chosenPersonalTrainer.split(" ");
        String personalTrainerID = chosenPersonalTrainerList[0].replace("[", "");



        boolean confirmed = sceneController.showConfirmationPopup("Weet je zeker dat je een nieuwe begeleider wil?");
        if (confirmed) {
            databaseManagement.updateKlantPersonalTrainer(username, 0, Integer.parseInt(personalTrainerID));
            setPersonalDetailsBox(username);
        }
    }
}
