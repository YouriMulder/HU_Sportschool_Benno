package controllers;

import database.databaseManagement;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.ArrayList;

public class abonnementController {

    // scene buttons and labels
    private String username = loginDetails.getCurrentUsername();
    public Button goToHomeButton;
    public Label usernameLabel;
    public ImageView profilePicture;

    // subscription box
    public ListView<String> abonnementenList;
    public Button veranderAbonnementButton;

    // stop subscription box
    public Button stopSubscriptionButton;

    // TODO auto set the label to the right String

    @FXML
    public void initialize() throws Exception {
        usernameLabel.setText(username);

        File file = new File("src/pictures/avatar/AvatarSample.png");
        Image profileImage = new Image(file.toURI().toString());
        profilePicture.setImage(profileImage);

        // inserts all the subscriptions to the box
        setAbonnementenBox();
    }

    @FXML
    public void goToHomeButtonPressed(javafx.event.ActionEvent event) throws Exception {
        sceneController.changeScene(event, getClass(), "mainScene");
    }

    @FXML
    public void goToDashboardButtonPressed(javafx.event.ActionEvent event) throws Exception {
        sceneController.changeScene(event, getClass(), "dashboardScene");
    }

    @FXML
    public void veranderAbonnementButtonClicked() throws Exception {
        String chosenSubscription = abonnementenList.getSelectionModel().getSelectedItems().toString();
        // checks if you selected a row
        if (!chosenSubscription.isEmpty() && !chosenSubscription.equals("[]")) {
            String[] chosenSubscriptionList = chosenSubscription.split(" ");
            String abonnementID = chosenSubscriptionList[0].replace("[", "");

            // checks if the selected is the header
            if (!abonnementID.equals("ID")) {
                boolean confirmed = sceneController.showConfirmationPopup("Weet je zeker dat je een nieuw abonnement wil?\nAls je ok klikt ga je akkoord met de vooorwaarden.");
                if (confirmed) {
                    // checks if user already has a subscription
                    if (databaseManagement.checkSubscriptionExists(username, 0)) {
                        sceneController.showPopup("Beindig eerst je vorige abonnement voordat je een nieuwe afsluit.");
                    } else {
                        databaseManagement.insertSubscription(username, Integer.parseInt(abonnementID));
                    }
                }
            // if the header is selected
            } else {
                sceneController.showPopup("Dit is geen abonnement. Kies een geldig abonnement.");
            }
        // if there is no row selected show popup
        } else {
            sceneController.showPopup("Dit is geen abonnement. Kies een geldig abonnement.");
        }
    }

    public void setAbonnementenBox() throws Exception {
        ArrayList<ArrayList> abonnementsvormen;
        abonnementsvormen = databaseManagement.getAbonnementsvormenTable();
        ObservableList<String> items = abonnementenList.getItems();

        if (!abonnementsvormen.isEmpty()) {
            // sets a header for the table
            String mainString = String.format("%-20s %-40s %-125s %-15s", "ID", "Naam", "Beschrijving", "Prijs");
            items.add(mainString);

            // gets every array one by one
            for (ArrayList abonnement : abonnementsvormen) {
                String abonnements_id = (String) abonnement.get(0);
                String abonnementsnaam = (String) abonnement.get(1);
                String beschrijving = (String) abonnement.get(2);
                String prijs = (String) abonnement.get(3);

                // inserts all the values in the list
                String abonnementString = String.format("%-20s %-40s %-125s %-15s", abonnements_id, abonnementsnaam, beschrijving, prijs);
                items.add(abonnementString);

            }
        } else {
            items.add("Er zijn geen abonnementen beschikbaar om uit te kiezen.");
        }
    }

    public void stopSubscriptionButtonPressed() throws Exception {
        databaseManagement.removeSubscriptionRow(username);
        sceneController.showPopup("Jou huidige abonnement is opgezegd.");
    }


}