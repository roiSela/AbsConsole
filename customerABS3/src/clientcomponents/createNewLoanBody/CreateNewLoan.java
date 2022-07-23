package clientcomponents.createNewLoanBody;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import loginPage.loginPageController;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import org.jetbrains.annotations.NotNull;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CreateNewLoan {

    @FXML
    private TextField loanIdTextBox;

    @FXML
    private TextField categoryTextBox;

    @FXML
    private TextField capitalTextBox;

    @FXML
    private TextField totalYazTextBox;

    @FXML
    private TextField paymentRateTextBox;

    @FXML
    private TextField intristTextBox;

    @FXML
    private Button submitNewLoanButton;
    private loginPageController mainController;

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    String customerName = "";
    @FXML
    void submitNewLoanButtonPressed(ActionEvent event) {
        customerName = mainController.getCustomerName();
        String loanId = loanIdTextBox.getText();
        String category = categoryTextBox.getText();
        String capital = capitalTextBox.getText();
        String totalYaz = totalYazTextBox.getText();
        String paymentRate = paymentRateTextBox.getText();
        String intrist = intristTextBox.getText();
        //send http request to submit new loan servlet
        String finalUrl = HttpUrl
                .parse(Constants.CREATE_NEW_LOAN)
                .newBuilder()
                .addQueryParameter("customerName", customerName)
                .addQueryParameter("loanId", loanId)
                .addQueryParameter("category", category)
                .addQueryParameter("capital", capital)
                .addQueryParameter("totalYaz", totalYaz)
                .addQueryParameter("paymentRate", paymentRate)
                .addQueryParameter("intrist", intrist)
                .build()
                .toString();
        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                System.out.println("Failed to submit new loan");
            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                System.out.println("Successfully submitted new loan");
                Platform.runLater(() -> {
                    //change button text to "Loan Submitted"
                    submitNewLoanButton.setText("Loan Submitted");
                });
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                Platform.runLater(() -> {
                    submitNewLoanButton.setText("Submit New Loan");
                });
            }
        });
        }

    public void setMainController(loginPageController loginPageController) {
        this.mainController = loginPageController;
    }
}

