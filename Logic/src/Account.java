import java.util.*;

public class Account {
    private List<Transaction> clientTransactions;
    private double currentBalance;


    public List<Transaction> getClientTransactions() {
        return clientTransactions;
    }
    public double getCurrentBalance() {
        return currentBalance;
    }
}
