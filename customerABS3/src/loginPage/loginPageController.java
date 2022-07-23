package loginPage;

import clientcomponents.clientinformationbody.ClientInformationBodyController;
import clientcomponents.clientpaymenybody.СlientPaymentBodyController;
import clientcomponents.clientscramblebody.ClientScrambleBody;
import clientcomponents.createNewLoanBody.CreateNewLoan;
import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;

import customerHeader.CustomerHeaderController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Bank;
import model.Customer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.concurrent.CompletableFuture;

import static util.Constants.REFRESH_RATE;

public class loginPageController {

    @FXML
    private TextField textFieldForName;

    @FXML
    private Button submitNameButton;

    Bank businessLogic;

    private final StringProperty errorMessageProperty = new SimpleStringProperty();
    private BorderPane root;
    private Stage primaryStage;

    //Refresh
    BusinessLogicRefresher businessLogicRefresher;
    private Timer timer;

    //other components
    private ScrollPane clientInformationComponent;
    private ClientInformationBodyController clientInformationComponentController;

    private ScrollPane clientPaymentComponent;
    private СlientPaymentBodyController clientPaymentComponentController;

    private ScrollPane clientScrambleComponent;
    private ClientScrambleBody clientScrambleComponentController;

    private HBox clientHeaderComponent;
    private CustomerHeaderController customerHeaderComponentController;

    private ScrollPane CreateNewLoanComponent;
    private CreateNewLoan createNewLoanComponentController;

    private Customer currentCustomer = null;



    String customerName = "";

    @FXML
    public void initialize() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/clientcomponents/clientinformationbody/clientInformationBody.fxml");
        fxmlLoader.setLocation(url);
        this.clientInformationComponent = fxmlLoader.load(url.openStream());
        this.clientInformationComponentController = fxmlLoader.getController();
        this.clientInformationComponentController.setMainController(this);


        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("/clientcomponents/clientpaymenybody/clientPaymentBody.fxml");
        fxmlLoader.setLocation(url);
        this.clientPaymentComponent = fxmlLoader.load(url.openStream());
        this.clientPaymentComponentController = fxmlLoader.getController();
        this.clientPaymentComponentController.setMainController(this);


        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("/clientcomponents/clientscramblebody/clientScrambleBody.fxml");
        fxmlLoader.setLocation(url);
        this.clientScrambleComponent = fxmlLoader.load(url.openStream());
        this.clientScrambleComponentController = fxmlLoader.getController();
        this.clientScrambleComponentController.setMainController(this);

        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("/customerHeader/customerHeader.fxml");
        fxmlLoader.setLocation(url);
        this.clientHeaderComponent = fxmlLoader.load(url.openStream());
        this.customerHeaderComponentController = fxmlLoader.getController();
        this.customerHeaderComponentController.setMainController(this);

        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("/clientcomponents/createNewLoanBody/createNewLoan.fxml");
        fxmlLoader.setLocation(url);
        this.CreateNewLoanComponent = fxmlLoader.load(url.openStream());
        this.createNewLoanComponentController = fxmlLoader.getController();
        this.createNewLoanComponentController.setMainController(this);
    }


    public void setCurrentCustomer(String name) {
        //get customer by name
        this.currentCustomer = businessLogic.getCustomerByName(name);

      /*  *//** ask server for the customer with the name we have! TO DO*//*
        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.CUSTOMER_BY_NAME)
                .newBuilder()
                .addQueryParameter("customerName", name)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            //new okhttp thread is running this code
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            errorMessageProperty.set("Something went wrong: " + responseBody)
                    );
                } else {
                    String json = response.body().string();
                    Customer customer = new Gson().fromJson(json, Customer.class);
                    currentCustomer = customer;
                    Platform.runLater(() -> {
                        //as for now, nothing to do here
                    });
                }
            }
        });
*/
    }


    public СlientPaymentBodyController getClientPaymentComponentController() {
        return clientPaymentComponentController;
    }


    public void startListRefresher() {
        businessLogicRefresher = new BusinessLogicRefresher(this);
        timer = new Timer();
        timer.schedule( businessLogicRefresher, REFRESH_RATE, REFRESH_RATE);
    }
    
    @FXML
    void submitNameButtonPressed(ActionEvent event) {
        String userName = textFieldForName.getText();
        if (userName.isEmpty()) {
            textFieldForName.setText("User name is empty. You can't login with empty user name");
            return;
        }

        //noinspection ConstantConditions
        String finalUrl = HttpUrl
                .parse(Constants.LOGIN_PAGE)
                .newBuilder()
                .addQueryParameter("username", userName)
                .build()
                .toString();

        System.out.println("New request is launched for: " + finalUrl); //debug

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            //new okhttp thread is running this code
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Platform.runLater(() ->
                        errorMessageProperty.set("Something went wrong: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() != 200) {
                    String responseBody = response.body().string();
                    Platform.runLater(() ->
                            errorMessageProperty.set("Something went wrong: " + responseBody)
                    );
                } else {
                    //send request to JAT to update the ui later, only the jat can update the ui
                    Platform.runLater(() -> {
                        customerName = userName;
                        customerHeaderComponentController.setCustomerName(customerName);
                        //clean root and change to the customer screen
                        root.getChildren().clear();
                        root.setCenter(clientInformationComponent);
                        root.setTop(clientHeaderComponent);
                        //tell logic to update every few seconds
                        startListRefresher();

                    });
                }
            }
        });
    }


    public void setRoot(BorderPane root) {
        this.root = root;
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.customerHeaderComponentController.setPrimaryStage(primaryStage);
    }

    public Customer getCurrentCustomer() {
        return currentCustomer;      //we keep him updated by the server with timer task
    }

    public Bank getBusinessLogic() { //we need to make sure the bank is updated all the time.
        return businessLogic;
    }

    public Node getClientScrambleComponent() {
        return clientScrambleComponent;
    }

    public Node getClientPaymentComponent() {
        return clientPaymentComponent;
    }

    public Node getClientInformationComponent() {
        return clientInformationComponent;
    }

    public void updateCustomerScrambleCategoriesListForCheckBox() {
        clientScrambleComponentController.addCategoriesToCheckBox(businessLogic.getLoanCategories());
    }


    public void updateLogic(Bank currentLogic) {
        this.businessLogic = currentLogic;
        setCurrentCustomer(customerName); //the info and payment make use of the current customer of the main controller so we must keep it updated.
        //tell scramble the logic as well, because unlike the other components he has an instance of bank in him
        clientScrambleComponentController.setBusinessLogic(currentLogic);
        clientScrambleComponentController.setCurrentCustomer(currentCustomer);

        customerHeaderComponentController.setBusinessLogic(currentLogic);

        //update tables
        clientInformationComponentController.updateTables(); /** make sure he has all the info he needs*/
        clientPaymentComponentController.updateTables(); /** make sure he has all the info he needs*/

    }

    public String getCustomerName() {
        return customerName;
    }

    public void changeToCreateNewLoan() {
        root.setCenter(CreateNewLoanComponent);
    }
}
