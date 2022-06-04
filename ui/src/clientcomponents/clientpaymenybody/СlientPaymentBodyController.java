package clientcomponents.clientpaymenybody;

import app.AppController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import model.classesForTables.LoanTableObj;
import model.classesForTables.MessagesTableObj;
import model.classesForTables.TransactionTableObj;

public class СlientPaymentBodyController {
    private AppController mainController;
    private Loan currentLoan = null;

    @FXML
    private TextField amountToPayToday;
    @FXML
    private TextField amountToPayForEverything;
    @FXML
    private TableView<MessagesTableObj> messageTable;

    @FXML
    private TableColumn<MessagesTableObj, String> nLoanName;
    @FXML
    private TableColumn<MessagesTableObj, String> nPaymentTime;
    @FXML
    private TableColumn<MessagesTableObj, String> nPaymentAmount;

    @FXML
    private TableView<LoanTableObj> loansToPay;

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


    @FXML
    public void initialize() {
        //loaner_id.setCellValueFactory(new PropertyValueFactory<LoanTableObj, String>("id"));
        nLoanName.setCellValueFactory(new PropertyValueFactory<MessagesTableObj, String>("nLoanName"));
        nPaymentAmount.setCellValueFactory(new PropertyValueFactory<MessagesTableObj, String>("nPaymentAmount"));
        nPaymentTime.setCellValueFactory(new PropertyValueFactory<MessagesTableObj, String>("nPaymentTime"));
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

    }

    public void updateTables()
    {

        ObservableList<MessagesTableObj> messagesList;
        ObservableList<LoanTableObj> loansToPayList;
        Customer currentCustomer = mainController.getCurrentCustomer();
        loansToPayList = mainController.getBusinessLogic().getLoans(currentCustomer.getLoansUnpaid());
        messagesList = currentCustomer.getMessagesForTable();
        messageTable.setItems(messagesList);
        loansToPay.setItems(loansToPayList);
    }



    public void setBodyToScramble(){

    }

    public void setCurrentLoan(){
            String currentLoanName = loansToPay.getSelectionModel().getSelectedItem().getId();
            this.currentLoan = mainController.getBusinessLogic().getLoanById(currentLoanName);
            String amountToPayTodayString = "amount to pay today :" + currentLoan.getAccumalatedDebt();
            String amountToPayForEverythingString = "amount to pay to finish :" + currentLoan.paymentAmountToFinishTheLoan();
            System.out.println(amountToPayTodayString);
            amountToPayToday.setText(amountToPayTodayString);
            amountToPayForEverything.setText(amountToPayForEverythingString);


    }

    public void payForToday()
    {

        double paymentAmmount =  currentLoan.getAccumalatedDebt();
        Customer customer = mainController.getCurrentCustomer();
        customer.removeFromLoansUnpaid(currentLoan);
        int currentTime = Bank.getCurrentTime();
        Transaction paymentToinvestor = new Transaction(paymentAmmount, currentTime, "+", customer.getCustomerAccount().getCurrentBalance(), customer.getCustomerAccount().getCurrentBalance() - paymentAmmount);
        Account customerAccount = customer.getCustomerAccount();
        currentLoan.getLoanPayments().add(paymentToinvestor);
        customerAccount .getCustomerTransactions().add(paymentToinvestor);
        customerAccount .setAccountBalance(customerAccount.getCurrentBalance() - paymentAmmount);
        currentLoan.updateLoanPayedSoFar(paymentAmmount);
        if(currentTime >= currentLoan.getStartingTime() + currentLoan.getTotalAmountOfTimeUnits()){
            currentLoan.setLoanStatusToFinished();
        }
        else{
            currentLoan.setLoanStatusToActive();
        }

        for(Investment investment : currentLoan.getInvestments()){
            double investmentPart = investment.getSizeOfInvestment() / currentLoan.getLoanAmount();
            double payment= paymentAmmount * investmentPart;
            Customer investedCustomer = mainController.getBusinessLogic().getCustomerByName(investment.getNameOfCustomer());
            mainController.getBusinessLogic().payInvestment(paymentAmmount, investedCustomer);
        }
        currentLoan.setAccumalatedDebt(0);
        setCurrentLoan();

    }

    public void payForToFinish(){
        double paymentAmmount = currentLoan.paymentAmountToFinishTheLoan();
        Customer customer = mainController.getCurrentCustomer();
        customer.removeFromLoansUnpaid(currentLoan);
        int currentTime = Bank.getCurrentTime();
        Transaction paymentToinvestor = new Transaction(paymentAmmount, currentTime, "+", customer.getCustomerAccount().getCurrentBalance(), customer.getCustomerAccount().getCurrentBalance() - paymentAmmount);
        currentLoan.getLoanPayments().add(paymentToinvestor);
        Account customerAccount = customer.getCustomerAccount();
        customerAccount .getCustomerTransactions().add(paymentToinvestor);
        customerAccount .setAccountBalance(customerAccount.getCurrentBalance() - paymentAmmount);
        currentLoan.updateLoanPayedSoFar(paymentAmmount);
        currentLoan.setLoanStatusToFinished();

        for(Investment investment : currentLoan.getInvestments()){
            double investmentPart = investment.getSizeOfInvestment() / currentLoan.getLoanAmount();
            double payment= paymentAmmount * investmentPart;
            Customer investedCustomer = mainController.getBusinessLogic().getCustomerByName(investment.getNameOfCustomer());
            mainController.getBusinessLogic().payInvestment(paymentAmmount, investedCustomer);
        }
        currentLoan.setAccumalatedDebt(0);
        setCurrentLoan();

    }


    public void setBodyToInformation() {
        if (mainController != null) {
            mainController.getRoot().setCenter(mainController.getClientInformationComponent());
        }
    }



    public void setMainController(AppController mainController) {this.mainController = mainController;}
}


