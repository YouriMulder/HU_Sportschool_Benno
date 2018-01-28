package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class sceneController {
    /*
    The parameter Class c was needed because this is another class than the one who called the function. c is now the correct class where the function is called from
     */
    public static void changeScene(javafx.event.ActionEvent event, Class c, String scene) throws Exception{
        // load scenes
        Parent loginScene = FXMLLoader.load(c.getResource("../scenes/" + scene + ".fxml"));
        loginScene.getStylesheets().add("css/global.css");

        // getting window
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        primaryStage.setScene(new Scene(loginScene));
        primaryStage.show();
    }

    public static void createRegisterStage(javafx.event.ActionEvent event, Class c) throws Exception{
        // creates the stage for the register scene
        Parent registerScene;
        registerScene = FXMLLoader.load(c.getResource("../scenes/registerScene.fxml"));
        registerScene.getStylesheets().add("css/register.css");

        // create new stage/window
        Stage registerStage = new Stage();
        registerStage.setTitle("Benno's sportschool - Maak een account aan!");
        registerStage.setScene(new Scene(registerScene, 450, 670));
        registerStage.setResizable(false);

        // sets always on top and unable to click on the other stage
        registerStage.initModality(Modality.APPLICATION_MODAL);
        registerStage.show();
    }

    public static void closeStage(Stage stage){
        stage.close();
    }

    public static void showErrorPopup(String errorHeader, String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error popup");
        alert.setHeaderText(errorHeader);
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    public static void showPopup(String infoMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information popup");
        alert.setHeaderText(null);
        alert.setContentText(infoMessage);

        alert.showAndWait();
    }

    public static boolean showConfirmationPopup(String confirmMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation popup");
        alert.setHeaderText(null);
        alert.setContentText(confirmMessage);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return true;
        } else {
           return false;
        }
    }

    public static String showInputPopup(String inputHeader, String inputMessage) {
        TextInputDialog dialog = new TextInputDialog("walter");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText(inputHeader);
        dialog.setContentText(inputMessage);

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }
        return "";
    }
}
