package AdminEntryScene;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AdminEntryController<Bank> {

    @FXML
    private Label currenlyLoadedFileText;

    @FXML
    private Label CurrentYazText;

    @FXML
    private Button LoansButton;

    @FXML
    private Button customerInfoButton;

    @FXML
    private Button increaseYazButton;

    @FXML
    private Button LoadFileButton;

    private Bank businessLogic;
    private Stage primaryStage;

    public AdminEntryController(){

    }

    public void setBusinessLogic(Bank businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }


    @FXML
    void LoadFileButtonPressed(ActionEvent event) {
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

    @FXML
    void LoansButtonPressed(ActionEvent event) {

    }

    @FXML
    void customerInfoButtonPressed(ActionEvent event) {

    }

    @FXML
    void increaseYazButtonPressed(ActionEvent event) {

    }

}
