package loginPage;

import admincomponents.adminbody.AdminBodyController;
import com.sun.istack.internal.NotNull;
import adminheader.HeaderController;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Bank;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.net.URL;
import java.util.Timer;

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
    private ScrollPane adminHeader;
    private HeaderController adminHeaderController;

    private ScrollPane adminBody;
    private AdminBodyController adminBodyController;

    String adminName = "";

    @FXML
    public void initialize() throws IOException {
        //load admin header
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/adminheader/header.fxml");
        fxmlLoader.setLocation(url);
        this.adminHeader = fxmlLoader.load(url.openStream());
        this.adminHeaderController = fxmlLoader.getController();
        this.adminHeaderController.setMainController(this);

        fxmlLoader = new FXMLLoader();
        url = getClass().getResource("/admincomponents/adminbody/adminBody.fxml");
        fxmlLoader.setLocation(url);
        this.adminBody = fxmlLoader.load(url.openStream());
        this.adminBodyController = fxmlLoader.getController();
        this.adminBodyController.setMainController(this);




        /*
          FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("/clientcomponents/clientinformationbody/clientInformationBody.fxml");
        fxmlLoader.setLocation(url);
        this.clientInformationComponent = fxmlLoader.load(url.openStream());
        this.clientInformationComponentController = fxmlLoader.getController();
        this.clientInformationComponentController.setMainController(this);

         */
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
                        adminName = userName;
                        adminHeaderController.setAdminName(adminName); //update the header to write the name of the admin
                        //clean root and change to the customer screen
                        root.getChildren().clear();
                        root.setCenter(adminBody);
                        root.setTop(adminHeader);
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
    }


    public Bank getBusinessLogic() { //we need to make sure the bank is updated all the time.
        return businessLogic;
    }

    public void setBusinessLogic(Bank businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void tellAdminBodyToUpdate() {
        adminBodyController.updateAdminTables();
    }

    public void updateLogic(Bank currentLogic) {
        //System.out.println(currentLogic.getCurrentTime()); //debug
        //update logic in the header and body
        this.businessLogic = currentLogic;
        adminHeaderController.setBusinessLogic(currentLogic);
        adminBodyController.setBusinessLogic(currentLogic);
        //update tables and text fields
        tellAdminBodyToUpdate();
        adminHeaderController.updateCurrentYaz();
    }

    public String getAdminName() {
        return adminName;
    }

    public HeaderController getHeaderComponentController() {
        return adminHeaderController;
    }
}
