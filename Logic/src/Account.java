import java.util.*;

public class Account {
    private List<Transaction> customerTransactions;
    private double currentBalance;

    public Account(double initSum){
        currentBalance = initSum;
        customerTransactions = new ArrayList<>();
    }
    public List<Transaction> getCustomerTransactions() {
        return customerTransactions;
    }
    public double getCurrentBalance() {
        return currentBalance;
    }
}
