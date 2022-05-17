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
        URL url = getClass().getResource("../app/app.fxml");
        fxmlLoader.setLocation(url);
       BorderPane root = fxmlLoader.load(url.openStream());

        //show app the primary stage, and he will show it to the other components that need to see it, e.g the body, that needs it to the file chooser.
        AppController app = fxmlLoader.getController();
        app.setPrimaryStage(primaryStage);
        app.setBusinessLogic(new Bank());
        Scene scene = new Scene(root, 1400, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ABS with javaFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }

}
