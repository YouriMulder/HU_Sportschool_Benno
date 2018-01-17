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
    @FXML
    private ImageView imageView;

    @FXML
    private Button buttonSportschool;

    public Button goToLoginButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // list to store all the images of the gym
        ArrayList<Image> imageList = new ArrayList<>();

        // add all the images to the list
        addAllImagesToList(imageList);
        imageView.setImage(imageList.get(0));
        imageView.setFitWidth(imageView.getFitWidth());

        // image handler
        // first image changer
        buttonSportschool.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        imageView.setImage(imageList.get(1));
                    }
                });

        buttonSportschool.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        imageView.setImage(imageList.get(0));
                    }
                });
    }

    @FXML
    public void goToLoginButtonPressed(javafx.event.ActionEvent event) throws Exception {
        // load scenes
        Parent loginScene = FXMLLoader.load(getClass().getResource("../scenes/loginScene.fxml"));
        loginScene.getStylesheets().add("css/global.css");

        // getting window
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        primaryStage.setScene(new Scene(loginScene, primaryStage.getMaxWidth(), primaryStage.getHeight()));
        primaryStage.show();


    }

    private void addAllImagesToList(ArrayList imageList) {

        ArrayList<String> pathList = new ArrayList<>();
        pathList.add("fitness_1");
        pathList.add("fitness_2");
        pathList.add("fitness_3");
        pathList.add("fitness_4");
        pathList.add("fitness_5");
        pathList.add("pool_1");
        pathList.add("pool_2");
        pathList.add("spa_1");

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