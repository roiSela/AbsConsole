package model;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import model.classesForTables.LoanTableObj;
import model.classesForTables.TransactionTableObj;

import java.io.Serializable;
import java.util.*;

public class Account implements Serializable {
    private List<Transaction> customerTransactions;
    private double currentBalance;

    public Account(double initSum) {
        currentBalance = initSum;
        customerTransactions = new ArrayList<>();
    }

    public void putMoneyInAccount(double moneyToLoad, int currentTime) {
        currentBalance = currentBalance + moneyToLoad;
        double balanceBeforeTransaction = currentBalance - moneyToLoad;
        Transaction newTransaction = new Transaction(moneyToLoad, currentTime, "+", balanceBeforeTransaction, currentBalance);
        customerTransactions.add(newTransaction);
    }

    public void takeMoneyFromAccount(double moneyToTake, int currentTime) {
        currentBalance = currentBalance - moneyToTake;
        double balanceBeforeTransaction = currentBalance + moneyToTake;
        Transaction newTransaction = new Transaction(moneyToTake, currentTime, "-", balanceBeforeTransaction, currentBalance);
        customerTransactions.add(newTransaction);

    }

    public List<Transaction> getCustomerTransactions() {
        return customerTransactions;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public String accountTransactionsToString() {
        String temp = "";

        for (Transaction transaction : customerTransactions) {
            temp += transaction.toString();
        }
        return temp;
    }

    public void setAccountBalance(double accountBalance) {
        this.currentBalance = accountBalance;
    }

    public ObservableList<TransactionTableObj> getTransactions() {
        ObservableList<TransactionTableObj> transactionForTable = FXCollections.observableArrayList();
        for (Transaction transaction : customerTransactions) {
            String time = String.valueOf(transaction.getTransactionTime());
            String sum = String.valueOf(transaction.getTransactionAmount());
            String type = transaction.getTransactionType();
            String balanceBefore = String.valueOf(transaction.getBalanceBeforeTransaction());
            String balanceAfter = String.valueOf(transaction.getBalanceAfterTransaction());

            TransactionTableObj obj = new TransactionTableObj(time, sum, type, balanceBefore, balanceAfter);
            transactionForTable.add(obj);

        }
        return transactionForTable;

    }
}
