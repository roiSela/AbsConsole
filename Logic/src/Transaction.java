public class Transaction {
    private int transactionTime;
    private double transactionAmount;
    private double fundComponent;
    private double interestComponent;
    //note: transactionAmount = fundComponent + interestComponent;
    private String transactionType;
    private double balanceBeforeTransaction;
    private double balanceAfterTransaction;

    private boolean transactionPassedSuccesfully; //sometimes, because of lack of funds from customer, a loan turns to "in-risk" mode, we need to mark this transaction so we can complete it
    //in the future.

    public double getFundComponent() {
        return fundComponent;
    }

    public double getInterestComponent() {
        return interestComponent;
    }

    public int getTransactionTime() {
        return transactionTime;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getBalanceBeforeTransaction() {
        return balanceBeforeTransaction;
    }

    public double getBalanceAfterTransaction() {
        return balanceAfterTransaction;
    }

    public boolean isTransactionPassedSuccesfully() {
        return transactionPassedSuccesfully;
    }


}
