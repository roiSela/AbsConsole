package customerHeader;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import loginPage.loginPageController;
import model.Bank;
import model.Customer;
import okhttp3.*;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.File;
import java.io.IOException;

import static util.http.HttpClientUtil.HTTP_CLIENT;

public class CustomerHeaderController {

    @FXML
    private Button informationButton;

    @FXML
    private Button paymentButton;

    @FXML
    private Button scrambleButton;

    @FXML
    private Button loadFileButton;

    @FXML
    private Button createNewLoanButton;

    @FXML
    private Button sellOrBuyLoansButton;

    public void setCustomerName(String customerName) {
       this.customerName.setText(customerName);
    }

    @FXML
    private Label customerName;

    private Stage primaryStage;
    private loginPageController mainController;


    Bank businessLogic;



    @FXML
    void createNewLoanButtonPressed(ActionEvent event) {
        mainController.changeToCreateNewLoan();
    }

    @FXML
    void loadFileButtonPressed(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select xml data file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        } else {
            String absolutePath = selectedFile.getAbsolutePath();
            File f = new File(absolutePath);
            String customerName = mainController.getCustomerName();
            RequestBody body =
                    new MultipartBody.Builder()
                            .addFormDataPart("file1", f.getName(), RequestBody.create(f, MediaType.parse("text/xml")))
                            .addFormDataPart("customerName",customerName ) // you can add multiple, different parts as needed
                            .build();

           // System.out.println(mainController.getCurrentCustomer().getCustomerName());

            Request request = new Request.Builder()
                    .url(Constants.LOAD_FILE)
                    .post(body)
                    .build();

            Call call = HTTP_CLIENT.newCall(request); /** I made this an  HTTP_CLIENT static instance and made it public (up in this file) is that ok? */

            Response response = call.execute();
        }
    }

    @FXML
    void informationButtonPressed(ActionEvent event) {
        //make root pane to be the information pane
        mainController.getRoot().setCenter(mainController.getClientInformationComponent());
    }

    @FXML
    void paymentButtonPressed(ActionEvent event) {
        //make root pane to be the payment pane
        mainController.getRoot().setCenter(mainController.getClientPaymentComponent());
    }

    @FXML
    void scrambleButtonPressed(ActionEvent event) {
        //make root pane to be the scramble pane
        mainController.getRoot().setCenter(mainController.getClientScrambleComponent());
       mainController.updateCustomerScrambleCategoriesListForCheckBox();
    }

    @FXML
    void sellOrBuyLoansButtonPressed(ActionEvent event) {

    }

    public void setBusinessLogic(Bank businessLogic) {
        this.businessLogic = businessLogic;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setMainController(loginPageController loginPageController) {
        mainController = loginPageController;
    }
}
