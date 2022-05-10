package adminbody;

import app.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Bank;

import java.io.File;

public class AdminBodyController {

    @FXML
    private Button increaseYazButton;

    @FXML
    private Button loadFileButton;

    @FXML
    private TableView<?> loansTable;

    @FXML
    private TableView<?> customersInformationTable;

    private AppController mainController;
    private Bank businessLogic;
    private Stage primaryStage;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
    public void setBusinessLogic(Bank businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void increaseYazButtonPressed(ActionEvent event) {

    }

    @FXML
    void loadFileButtonPressed(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml data file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }

        String absolutePath = selectedFile.getAbsolutePath();
        System.out.println(absolutePath);
    }

}
