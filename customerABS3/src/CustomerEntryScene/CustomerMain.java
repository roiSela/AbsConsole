package CustomerEntryScene;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import loginPage.loginPageController;
import model.Bank;

import java.net.URL;

public class CustomerMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        //URL url = getClass().getResource("/appCustomer/app.fxml");
        URL url = getClass().getResource("/loginPage/loginPage.fxml");
        fxmlLoader.setLocation(url);
        BorderPane root = fxmlLoader.load(url.openStream());
        //show app the primary stage, and he will show it to the other components that need to see it, e.g the body, that needs it to the file chooser.
        loginPageController app = fxmlLoader.getController();
        app.setRoot(root);
        app.setPrimaryStage(primaryStage);
        //app.setBusinessLogic(new Bank()); we dont need that anymore becuase only the server knows the logic
        Scene scene = new Scene(root, 1800, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("ABS with javaFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Thread.currentThread().setName("main");
        launch(args);
    }

}