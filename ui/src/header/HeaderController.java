package header;

import app.AppController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Bank;

import java.util.List;

public class HeaderController {

    @FXML
    private ComboBox<String> userModeComboBox;

    @FXML
    private Label currentYazLable;

    @FXML
    private TextField fileText;

    private AppController mainController;
    private Bank businessLogic;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }

    @FXML
    void changeUserMode(ActionEvent event) {

    }

    public void setBusinessLogic(Bank businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void changeFileText(String path) {
        fileText.setText(path);
    }

    public void updateUserModeComboBox(ObservableList<String> namesToAddToComboBox) {
       userModeComboBox.setItems(namesToAddToComboBox);
    }
}
