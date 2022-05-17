package app;

import admincomponents.adminbody.AdminBodyController;
import header.HeaderController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import model.Bank;

import java.util.List;


public class AppController {
    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private ScrollPane bodyComponent;
    @FXML private AdminBodyController bodyComponentController;
    private Stage primaryStage;
    private Bank businessLogic;

    public void setBusinessLogic(Bank businessLogic) {
        this.businessLogic = businessLogic;
        headerComponentController.setBusinessLogic(businessLogic);
        bodyComponentController.setBusinessLogic(businessLogic);
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        bodyComponentController.setPrimaryStage(primaryStage);
    }

    @FXML
    public void initialize() {
        headerComponentController.setMainController(this);
        bodyComponentController.setMainController(this);

    }
    public void changeFileText(String path) {
        headerComponentController.changeFileText(path);
    }

    public void updateUserModeComboBox(ObservableList<String> namesToAddToComboBox) {
        headerComponentController.updateUserModeComboBox(namesToAddToComboBox);
    }
}
