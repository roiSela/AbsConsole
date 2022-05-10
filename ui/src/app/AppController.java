package app;

import adminbody.AdminBodyController;
import header.HeaderController;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Bank;

import java.awt.*;

public class AppController {
    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private ScrollPane bodyComponent;
    @FXML private AdminBodyController bodyComponentController;
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() {
        headerComponentController.setMainController(this);
        bodyComponentController.setMainController(this);
    }

}
