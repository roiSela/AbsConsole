package clientcomponents.clientscramblebody;


import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import loginPage.loginPageController;
import model.Bank;
import model.Customer;
import model.Loan;
import model.classesForTables.LoanTableObj;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Response;
import org.controlsfx.control.CheckComboBox;
import org.jetbrains.annotations.NotNull;
import sun.plugin2.util.PojoUtil;
import util.Constants;
import util.http.HttpClientUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static util.Constants.GSON_INSTANCE;

public class ClientScrambleBody {

    @FXML
    private Button scrambleButton;

    @FXML
    private TextField sumToInvestText;

    @FXML
    private TextField minimumInterestText;

    @FXML
    private TextField minimumYazForLoanText;

    @FXML
    private TextField openLoansText;

    @FXML
    private TextField maximunOwenership;

    @FXML
    private CheckComboBox<String> categoryCheckBox;

    @FXML
    private CheckComboBox<String> loansChosen;

    @FXML
    private Button findLoansButton;

    @FXML
    private TableView<LoanTableObj> loansTable;

    @FXML
    private TableColumn<LoanTableObj, String> id;

    @FXML
    private TableColumn<LoanTableObj, String> owner;

    @FXML
    private TableColumn<LoanTableObj, String> category;

    @FXML
    private TableColumn<LoanTableObj, String> amount;

    @FXML
    private TableColumn<LoanTableObj, String> time;

    @FXML
    private TableColumn<LoanTableObj, String> interest;

    @FXML
    private TableColumn<LoanTableObj, String> rate;

    @FXML
    private TableColumn<LoanTableObj, String> status;

    @FXML
    private TableColumn<LoanTableObj, String> pending;

    @FXML
    private TableColumn<LoanTableObj, String> active;

    @FXML
    private TableColumn<LoanTableObj, String> risk;

    @FXML
    private TableColumn<LoanTableObj, String> finished;


    ObservableList<LoanTableObj> loansInformation;

    private loginPageController mainController;
    private Bank businessLogic;
    private Customer currentCustomer;

    @FXML
    public void initialize() {
        //initialize loansTable
        id.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("id"));
        owner.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("owner"));
        category.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("category"));
        amount.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("amount"));
        time.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("time"));
        interest.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("interest"));
        rate.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("rate"));
        status.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("status"));
        pending.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("pending"));
        active.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("active"));
        risk.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("risk"));
        finished.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("finished"));
        loansInformation = null;
    }

    @FXML
    void findLoansButtonPressed(ActionEvent event) {
        String sumToInvestText = this.sumToInvestText.getText();

        Set<String> categories = new HashSet<String>();
        ObservableList<String> selectedCategories = categoryCheckBox.getCheckModel().getCheckedItems();
        for (String category : selectedCategories) {
            categories.add(category);
        }

        String minimumInterestText = this.minimumInterestText.getText();
        String minimumTotalYazForLoanText = minimumYazForLoanText.getText();
        String MaximumOpenLoansText = openLoansText.getText();
        String maximumOwnershipText = maximunOwenership.getText();

        boolean sumToInvestIsValid = checkSumToInvest(sumToInvestText);
        boolean minimumInterestIsValid = checkMinimalInterest(minimumInterestText);
        boolean minimumYazForLoanIsValid = checkMinimalYazForLoan(minimumTotalYazForLoanText);
        boolean openLoansIsValid = checkOpenLoans(MaximumOpenLoansText);
        boolean maximumOwnershipIsValid = checkMaximumOwnership(maximumOwnershipText);

        if (sumToInvestIsValid && minimumInterestIsValid && minimumYazForLoanIsValid && openLoansIsValid && maximumOwnershipIsValid) {
            int customerIndex = businessLogic.getCustomerIndexByName(currentCustomer.getCustomerName());
            double sumToInvest = -1;
            if (!sumToInvestText.isEmpty()) {
                sumToInvest = Double.parseDouble(sumToInvestText);
            }
            categories = categories;//categories are fine
            double minimumInterest = -1;
            if (!minimumInterestText.isEmpty()) {
                minimumInterest = Double.parseDouble(minimumInterestText);
            }
            int minimumTotalYazForLoan = -1;
            if (!minimumTotalYazForLoanText.isEmpty()) {
                minimumTotalYazForLoan = Integer.parseInt(minimumTotalYazForLoanText);
            }
            int MaximumOpenLoans = -1;
            if (!MaximumOpenLoansText.isEmpty()) {
                MaximumOpenLoans = Integer.parseInt(openLoansText.getText());
            }
            List<Loan> filteredLoans = businessLogic.getFilteredLoans(customerIndex, sumToInvest, categories, minimumInterest, minimumTotalYazForLoan, MaximumOpenLoans);
            updateLoansInformationForTable(filteredLoans);
            findLoansButton.setText("Find Loans to invest in");

        } else {
            findLoansButton.setText("Invalid Input, please try again");
        }

    }

    private boolean checkSumToInvest(String sumToInvest) {
        if (isStringDouble(sumToInvest) && Double.parseDouble(sumToInvest) > 0 && Double.parseDouble(sumToInvest) <= currentCustomer.getCurrentBalance()) {
            return true;
        } else {
            return false;
        }
    }


    private boolean checkMinimalInterest(String minimalInterest) {
        if (minimalInterest.isEmpty()) {
            return true;
        }
        if (isStringDouble(minimalInterest) && Double.parseDouble(minimalInterest) > 0) {
            return true;
        } else {
            return false;
        }

    }

    private boolean checkMinimalYazForLoan(String minimalYazForLoan) {
        if (minimalYazForLoan.isEmpty()) {
            return true;
        }
        if (isStringInteger(minimalYazForLoan) && Double.parseDouble(minimalYazForLoan) > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkOpenLoans(String openLoans) {
        if (openLoans.isEmpty()) {
            return true;
        }
        if (isStringInteger(openLoans) && Double.parseDouble(openLoans) > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkMaximumOwnership(String maximumOwnership) {
        if (maximumOwnership.isEmpty()) {
            return true;
        }
        if (isStringInteger(maximumOwnership) && Double.parseDouble(maximumOwnership) > 0 && Double.parseDouble(maximumOwnership) < 100) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    void scrambleButtonPressed(ActionEvent event) {
        int customerIndex = businessLogic.getCustomerIndexByName(currentCustomer.getCustomerName());
        int moneyToInvest = Integer.parseInt(sumToInvestText.getText());
        String maximumOwnershipText = maximunOwenership.getText();//we already know it's valid from the get loans button
        int maxPercentage;
        if (maximumOwnershipText.isEmpty()) {
            maxPercentage = -1;
        } else {
            maxPercentage = Integer.parseInt(maximumOwnershipText);
        }

        Set<String> loanNamesChosen = new HashSet<String>();
        ObservableList<String> selectedLoans = loansChosen.getCheckModel().getCheckedItems();
        for (String loanName : selectedLoans) {
            loanNamesChosen.add(loanName);
        }

        List<Loan> loansToInvestIn = businessLogic.getLoansByNames(loanNamesChosen);


        //make http request to the server to schedule loans
        ScrambleDTO scrambleDTO = new ScrambleDTO(customerIndex, moneyToInvest, maxPercentage, loansToInvestIn);
        //convert to json
        String jsonScrambleDTO =  new Gson().toJson(scrambleDTO);
        //add json and query parameters to request
        String finalUrl = HttpUrl
                .parse(Constants.SCRAMBLE_LOANS)
                .newBuilder()
                .addQueryParameter("scrambleDTO", jsonScrambleDTO)
                .build()
                .toString();

        HttpClientUtil.runAsync(finalUrl, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Something went wrong with Chat Request # " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String rawBody = response.body().string();
                    ScrambleDTO scrambleDTORet = GSON_INSTANCE.fromJson(rawBody, ScrambleDTO.class);
                    List<Loan> filteredLoanList = scrambleDTORet.getLoansToInvestIn();
                    //tell main to update logic:
                    Platform.runLater(() -> {
                        updateLoansInformationForTable(filteredLoanList);
                    });
                } else {
                    System.out.println("Something went wrong with Request " + response.code());
                }
            }
        });

        //businessLogic.schedulingLoansToCustomer(customerIndex, moneyToInvest, loansToInvestIn, maxPercentage); // this part is done in the servlet
    }

    public void updateLoansInformationForTable(List<Loan> filteredLoans) {
        if (loansInformation != null) {
            loansInformation.clear();
        }
        loansInformation = businessLogic.getLoansInformationForTableFiltered(filteredLoans);
        loansTable.setItems(loansInformation);
        //add names of loans in loansInformation to comboBox
        loansChosen.getItems().clear();
        for (LoanTableObj loan : loansInformation) {
            loansChosen.getItems().add(loan.getId());
        }
    }

    public void addCategoriesToCheckBox(List<String> categories) {
        categoryCheckBox.getItems().clear();
        ObservableList<String> categoriesList = FXCollections.observableArrayList();
        for (String category : categories) {
            categoriesList.add(category);
        }
        categoryCheckBox.getItems().addAll(categoriesList);
    }

    private static boolean isStringDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isStringInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }


    public void setMainController(loginPageController mainController) {
        this.mainController = mainController;
    }

    public void setBusinessLogic(Bank businessLogic) {
        this.businessLogic = businessLogic;
    }

}