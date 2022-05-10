package AdminEntryScene;

import app.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Bank;
import java.net.URL;

public class MainOfJavaFx extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("C:\\Users\\rugh5\\Desktop\\java course\\hw\\gitRep\\ui\\src\\app\\app.fxml");
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load(url.openStream()); // why parent?

        //show app the primary stage, and he will show it to the other components that need to see it, e.g the body, that needs it to the file chooser.
        AppController app = fxmlLoader.getController();
        app.setPrimaryStage(primaryStage);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
/*        // load main fxml
        FXMLLoader loader = new FXMLLoader();
        URL mainFXML = getClass().getResource("AdminEntrySceneFxml.fxml");
        loader.setLocation(mainFXML);
        ScrollPane root = loader.load();

        // wire up controller
       AdminEntryController adminEntryController = loader.getController();
       Bank bank = new model.Bank();
       adminEntryController.setPrimaryStage(primaryStage);
       adminEntryController.setBusinessLogic(bank);

        // set stage
        primaryStage.setTitle("Alternative Banking System using javaFX");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();*/
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }

}
