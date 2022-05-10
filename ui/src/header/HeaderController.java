package header;

import app.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class HeaderController {

    @FXML
    private ComboBox<?> userModeComboBox;

    @FXML
    private Label currentYazLable;

    private AppController mainController;

    public void setMainController(AppController mainController) {
        this.mainController = mainController;
    }
    @FXML
    void changeUserMode(ActionEvent event) {

    }

}
