package adminheader;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import loginPage.loginPageController;
import model.Bank;

public class HeaderController {

    @FXML
    private ComboBox<String> userModeComboBox;

    @FXML
    private Label currentYazLable;

    @FXML
    private TextField fileText;

    private loginPageController mainController;
    private Bank businessLogic;

    public void setMainController(loginPageController mainController) {
        this.mainController = mainController;
    }

/*    @FXML
    void changeUserMode(ActionEvent event) {
        if(userModeComboBox.getValue() == "Admin"){
            mainController.getRoot().setCenter(mainController.getBodyComponent());
            mainController.tellAdminBodyToUpdate();
        }
        else{
            mainController.getRoot().setCenter(mainController.getClientInformationComponent());
            mainController.setCurrentCustomer(businessLogic.getCustomerByName(userModeComboBox.getValue()));
            mainController.getClientInformationComponentController().updateTables();
            mainController.updateCustomerScrambleCategoriesListForCheckBox();
        }

    }*/
    public void updateCurrentYaz(){
        int currentTime = mainController.getBusinessLogic().getCurrentTime();
        currentYazLable.setText(String.valueOf(currentTime));
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

    public void setAdminName(String adminName) {
        fileText.setText("The name of the admin is:" + adminName);
    }

    @FXML
    public void changeUserMode(ActionEvent actionEvent) {
    }
}
