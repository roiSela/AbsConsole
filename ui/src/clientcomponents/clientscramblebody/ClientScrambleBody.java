package clientcomponents.clientscramblebody;

import app.AppController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Bank;
import model.Customer;
import model.classesForTables.LoanTableObj;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

        private AppController mainController;
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

                List<String> categories = new ArrayList<>();
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
                        
                }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error, the input is invalid");
                }

        }

        private boolean checkSumToInvest(String sumToInvest) {
                if (sumToInvest.isEmpty()) {
                        return true;
                }
                        if (isStringDouble(sumToInvest) && Double.parseDouble(sumToInvest) > 0 && Double.parseDouble(sumToInvest) <= currentCustomer.getCurrentBalance()) {
                                return true;
                        } else {
                               return false;
                        }
        }


        private boolean checkMinimalInterest(String minimalInterest) {
                if (minimalInterest.isEmpty()){
                        return true;
                }
                if (isStringDouble(minimalInterest) && Double.parseDouble(minimalInterest) > 0 ) {
                        return true;
                }else {
                        return false;
                }

        }

        private boolean checkMinimalYazForLoan(String minimalYazForLoan) {
                if (minimalYazForLoan.isEmpty()){
                        return true;
                }
                if (isStringInteger(minimalYazForLoan) && Double.parseDouble(minimalYazForLoan) > 0 ) {
                        return true;
                }else {
                        return false;
                }
        }

        private boolean checkOpenLoans(String openLoans) {
                if (openLoans.isEmpty()){
                        return true;
                }
                if (isStringInteger(openLoans) && Double.parseDouble(openLoans) > 0 ) {
                        return true;
                }else {
                        return false;
                }
        }

        private boolean checkMaximumOwnership(String maximumOwnership) {
                if (maximumOwnership.isEmpty()){
                        return true;
                }
                if (isStringInteger(maximumOwnership) && Double.parseDouble(maximumOwnership) > 0 &&  Double.parseDouble(maximumOwnership) < 100) {
                        return true;
                }else {
                        return false;
                }
        }

        @FXML
        void scrambleButtonPressed(ActionEvent event) {

        }

        public void updateLoansInformationForTable() {
                if (loansInformation != null) {
                        loansInformation.clear();
                }
                loansInformation = businessLogic.getLoansInformationForTable();
                loansTable.setItems(loansInformation);
        }

        void addCategoriesToCheckBox(List<String> categories) {
                ObservableList<String> categoriesList = FXCollections.observableArrayList();
                for (String category : categories) {
                        categoriesList.add(category);
                }
                categoryCheckBox.getItems().addAll(categoriesList);
        }


        private static int chooseMaximalLoanTime(Bank bank, Scanner input) {
                System.out.println("If you wish, choose a maximal loan time for loan you are willing to invest in (press enter if you are not interested in this filter):");
                String maximalLoanTime = input.nextLine();
                int maximalLoanTimeInt = -1; //if the user does not choose a maximal loan time, we set it to -1, that means that we will match all the loans loan times
                if (isStringInteger(maximalLoanTime)) {
                        maximalLoanTimeInt = Integer.parseInt(maximalLoanTime);
                }
                return maximalLoanTimeInt;
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


        public void setMainController(AppController mainController) {
                this.mainController = mainController;
        }

        public void setBusinessLogic(Bank businessLogic) {
                this.businessLogic = businessLogic;
        }

}

