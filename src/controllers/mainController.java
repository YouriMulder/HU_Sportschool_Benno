package controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class mainController implements Initializable {
    // the image in the middle of the screen
    public ImageView imageView;

    // navigation bar buttons
    public Button buttonPersoonlijkAdvies;
    public Button buttonAbonnementen;
    public Button buttonFaciliteiten;
    public Button buttonGroepslessen;
    public Button buttonICTVoorzieningen;
    public Button buttonDashboard;
    public Button buttonOverOns;

    // go to another page buttons
    public Button goToLoginButton;
    public Button goToRegisterButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // list to store all the images of the gym
        ArrayList<Image> imageList = new ArrayList<>();

        // add all the images to the list
        addAllImagesToList(imageList);
        imageView.setImage(imageList.get(0));
        imageView.setFitWidth(imageView.getFitWidth());
        imageView.setFitHeight(imageView.getFitHeight());

        // image handler
        // button 1 onze sportschool
        buttonPersoonlijkAdvies.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> imageView.setImage(imageList.get(1)));

        buttonPersoonlijkAdvies.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> imageView.setImage(imageList.get(0)));

        // button 2 abonnementen
        buttonAbonnementen.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> imageView.setImage(imageList.get(2)));

        buttonAbonnementen.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> imageView.setImage(imageList.get(0)));

        // button 3 faciliteiten
        buttonFaciliteiten.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> imageView.setImage(imageList.get(3)));

        buttonFaciliteiten.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> imageView.setImage(imageList.get(0)));

        // button 4 groepslessen
        buttonGroepslessen.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> imageView.setImage(imageList.get(4)));

        buttonGroepslessen.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> imageView.setImage(imageList.get(0)));

        // button 5 ICT voorzieningen
        buttonICTVoorzieningen.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> imageView.setImage(imageList.get(5)));

        buttonICTVoorzieningen.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> imageView.setImage(imageList.get(0)));

        // button 6 dashboard
        buttonDashboard.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> imageView.setImage(imageList.get(6)));

        buttonDashboard.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> imageView.setImage(imageList.get(0)));

        // button 7 over ons
        buttonOverOns.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> imageView.setImage(imageList.get(7)));

        buttonOverOns.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> imageView.setImage(imageList.get(0)));
    }

    @FXML
    public void goToLoginButtonPressed(javafx.event.ActionEvent event) throws Exception {
        sceneController.changeScene(event, getClass(), "loginScene");
    }

    @FXML
    public void goToRegisterButtonPressed(javafx.event.ActionEvent event) throws Exception {
        sceneController.createRegisterStage(event, getClass());
    }

    private void addAllImagesToList(ArrayList imageList) {

        ArrayList<String> pathList = new ArrayList<>(8);
        // standaard image
        pathList.add("standaard");
        pathList.add("persoonlijkAdvies");
        pathList.add("abonnementen");
        pathList.add("faciliteiten");
        pathList.add("groepslessen");
        pathList.add("ICTVoorzieningen");
        pathList.add("dashboard");
        pathList.add("overOns");

        for (String path : pathList) {
            addImageToList(imageList, "src/pictures/" + path + ".jpg");
        }
    }

    private void addImageToList (ArrayList imageList, String imagePath) {
        File file = new File(imagePath);
        Image image = new Image(file.toURI().toString());
        imageList.add(image);
    }
}