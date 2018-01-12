import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    // init all the window variables
    private int windowWidth = 600;
    private int windowHeight = 400;
    private int minWindowWidth = windowWidth;
    private int minWindowHeight = windowHeight;


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("scenes/mainScene.fxml"));

        // creating window
        primaryStage.setTitle("Account management");
        primaryStage.setScene(new Scene(root, windowWidth, windowHeight));
        primaryStage.setMinWidth(minWindowWidth);
        primaryStage.setMinHeight(minWindowHeight);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
