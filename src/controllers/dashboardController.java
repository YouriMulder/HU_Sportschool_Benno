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
    private String username = loginDetails.getCurrentUsername();

    public ListView<String> sessiesList;
    public Label usernameLabel;
    public Button goToHomeButton;
    public ImageView profilePicture;

    @FXML
    public void initialize() throws Exception {
        usernameLabel.setText(username);
        loginDetails.init();

        File file = new File("src/pictures/avatar/AvatarSample.png");
        Image profileImage = new Image(file.toURI().toString());
        profilePicture.setImage(profileImage);

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

}
