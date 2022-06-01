package model;

import generated.AbsCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.classesForTables.LoanTableObj;
import model.classesForTables.MessagesTableObj;
import model.classesForTables.TransactionTableObj;

import java.io.Serializable;
import java.util.*;

public class Customer implements Serializable {
    private String name;
    private Account account;
    private List<String> idListOfLoansThatCustomerInvestedIn; //all the loads that we invested in
    private List<Loan> loansCustomerCreated;
    private List<Loan> loansUnpaid ; ///list to save unpaid loans
    private List<Message> customerMessage; ///Message about loans paymaent

    public Customer(AbsCustomer copyFrom) {
        name = copyFrom.getName();
        account = new Account(copyFrom.getAbsBalance());
        idListOfLoansThatCustomerInvestedIn = new ArrayList<>();
        loansCustomerCreated = new ArrayList<>();
        customerMessage = new ArrayList<>();
        loansUnpaid = new ArrayList<>();
    }

    public void addCreatedLoan(Loan loan) {
        loansCustomerCreated.add(loan);
    }
    public void addInvestedLoan(String id) {
        if (!idListOfLoansThatCustomerInvestedIn.contains(id)) {
            idListOfLoansThatCustomerInvestedIn.add(id);
        }
    }

    public void payForLoan(Loan loan, int currentTime, Bank bank) {
        Account customerAccount = account;
        Transaction loanPayment = new Transaction(loan.getMoneyTopay(currentTime), currentTime, "-", customerAccount.getCurrentBalance(), customerAccount.getCurrentBalance() + loan.getMoneyTopay(currentTime));
        customerAccount.getCustomerTransactions().add(loanPayment);
        customerAccount.setAccountBalance(customerAccount.getCurrentBalance() - loan.getMoneyTopay(currentTime));
        loan.getLoanPayments().add(loanPayment);
        loan.setAccumalatedDebt(0);
        if (currentTime >= loan.getStartingTime() + loan.getTotalAmountOfTimeUnits()) {
            loan.setLoanStatus(Loan.LoanStatus.FINISHED);
        } else {
            loan.setLoanStatus(Loan.LoanStatus.ACTIVE);
        }

        for (Investment investment : loan.getInvestments()) {
            double investmentPart = investment.getSizeOfInvestment() / loan.getLoanAmount();
            double paymentAmmount = loan.getMoneyTopay(currentTime) * investmentPart;
            Customer investedCustomer = bank.getCustomerByName(investment.getNameOfCustomer());
            bank.payInvestment(paymentAmmount, investedCustomer);

        }
    }

    public ObservableList<MessagesTableObj> getMessagesForTable() {
        ObservableList<MessagesTableObj> MessagesForTable = FXCollections.observableArrayList();
        for (Message message : customerMessage) {
            //String time = String.valueOf(transaction.getTransactionTime());
            String loanName = message.getLoanName();
            String time = String.valueOf(message.getPaymentTime());
            String paymentAmount = String.valueOf(message.getPaymentAmount());
            MessagesTableObj obj = new MessagesTableObj(loanName, time, paymentAmount);
            MessagesForTable.add(obj);
        }
        return MessagesForTable;
    }

    public int getNumOfOpenLoans(){
        int numOfOpenLoans = 0;
        for (Loan loan : loansCustomerCreated) {
            if (loan.getStatus() == Loan.LoanStatus.ACTIVE || loan.getStatus() == Loan.LoanStatus.NEW || loan.getStatus() == Loan.LoanStatus.RISK|| loan.getStatus() == Loan.LoanStatus.PENDING) {
                numOfOpenLoans++;
            }
        }
        return numOfOpenLoans;
    }

    public double getCurrentBalance(){return account.getCurrentBalance();}



    public String getCustomerTransactionsString(){return account.accountTransactionsToString();}
    public String getCustomerName() {return name;}
    public Account getCustomerAccount() {return account;}
    public List<String> getIdListOfLoansThatCustomerInvestedIn() {return idListOfLoansThatCustomerInvestedIn;}
    public List<Loan> getLoansCustomerCreated() {return loansCustomerCreated;}
    public List<Loan> getLoansUnpaid() {return loansUnpaid;}

    public List<Message> getCustomerMessage() {return customerMessage;}

    public void setCustomerMessage(List<Message> customerMessage) {this.customerMessage = customerMessage;}
}
