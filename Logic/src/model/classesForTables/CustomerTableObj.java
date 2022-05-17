package model.classesForTables;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class CustomerTableObj {
    String customerName;

    String customerBalance;

    String loansWeInvestedIn;

    String loansWeCreated;

    public CustomerTableObj(String customerName, String customerBalance, String loansWeInvestedIn, String loansWeCreated) {
        this.customerName = customerName;
        this.customerBalance = customerBalance;
        this.loansWeInvestedIn = loansWeInvestedIn;
        this.loansWeCreated = loansWeCreated;
    }
    public CustomerTableObj(){

    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerBalance() {
        return customerBalance;
    }

    public void setCustomerBalance(String customerBalance) {
        this.customerBalance = customerBalance;
    }

    public String getLoansWeInvestedIn() {
        return loansWeInvestedIn;
    }

    public void setLoansWeInvestedIn(String loansWeInvestedIn) {
        this.loansWeInvestedIn = loansWeInvestedIn;
    }

    public String getLoansWeCreated() {
        return loansWeCreated;
    }

    public void setLoansWeCreated(String loansWeCreated) {
        this.loansWeCreated = loansWeCreated;
    }
}
