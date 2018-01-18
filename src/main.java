import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class main extends Application {

    // init all the window variables
    private static final int windowWidth = 1280;
    private static final int windowHeight = 720;
    private int minWindowWidth = windowWidth;
    private int minWindowHeight = windowHeight;

    @Override
    public void start(Stage primaryStage) throws Exception{

        // load scenes
        Parent mainScene = FXMLLoader.load(getClass().getResource("scenes/mainScene.fxml"));

        // load css
        mainScene.getStylesheets().add("css/global.css");

        // creating window
        primaryStage.setTitle("Benno's sportschool - Menu");
        primaryStage.setScene(new Scene(mainScene, windowWidth, windowHeight));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
