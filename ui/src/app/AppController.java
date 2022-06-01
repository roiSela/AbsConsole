package app;

import admincomponents.adminbody.AdminBodyController;
import clientcomponents.clientinformationbody.ClientInformationBodyController;
import clientcomponents.clientpaymenybody.СlientPaymentBodyController;
import clientcomponents.clientscramblebody.ClientScrambleBody;
import header.HeaderController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Bank;
import model.Customer;

import java.io.IOException;
import java.net.URL;


public class AppController {
    @FXML private ScrollPane headerComponent;
    @FXML private HeaderController headerComponentController;
    @FXML private ScrollPane bodyComponent;
    @FXML private AdminBodyController bodyComponentController;
     private ScrollPane clientInformationComponent;
     private ClientInformationBodyController clientInformationComponentController;
     private ScrollPane clientPaymentComponent;
     private СlientPaymentBodyController clientPaymentComponentController;
     private ScrollPane clientScrambleComponent;
     private ClientScrambleBody clientScrambleComponentController;

    private BorderPane root;
    private Stage primaryStage;
    private Bank businessLogic;
    private Customer currentCustomer = null;

    public AppController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("../clientcomponents/clientinformationbody/clientInformationBody.fxml");
        fxmlLoader.setLocation(url);
        this.clientInformationComponent = fxmlLoader.load(url.openStream());
        this.clientInformationComponentController = fxmlLoader.getController();
        this.clientInformationComponentController.setMainController(this);


        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("../clientcomponents/clientpaymenybody/clientPaymentBody.fxml");
        fxmlLoader.setLocation(url);
        this.clientPaymentComponent = fxmlLoader.load(url.openStream());
        this.clientPaymentComponentController= fxmlLoader.getController();
        this.clientPaymentComponentController.setMainController(this);

        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("../clientcomponents/clientscramblebody/clientScrambleBody.fxml");
        fxmlLoader.setLocation(url);
        this.clientScrambleComponent = fxmlLoader.load(url.openStream());
        this.clientScrambleComponentController= fxmlLoader.getController();
        this.clientScrambleComponentController.setMainController(this);



    }

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

    public BorderPane getRoot() {
        return root;
    }

    public ScrollPane getBodyComponent() {
        return bodyComponent;
    }

    public ScrollPane getClientInformationComponent() {
        return clientInformationComponent;
    }

    public ClientInformationBodyController getClientInformationComponentController() {return clientInformationComponentController;}

    public Bank getBusinessLogic() {return businessLogic;}

    public Customer getCurrentCustomer() {return currentCustomer;}

    public ScrollPane getClientScrambleComponent() {return clientScrambleComponent;}

    public ScrollPane getClientPaymentComponent() {return clientPaymentComponent;}

    public СlientPaymentBodyController getClientPaymentComponentController() {return clientPaymentComponentController;}

    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public void setCurrentCustomer(Customer customer) {
        this.currentCustomer = customer;
    }

}