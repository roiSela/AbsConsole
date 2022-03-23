public class Transaction {
    private int transactionTime;
    private double transactionAmount;
    private double fundComponent;
    private double interestComponent;
    //note: transactionAmount = fundComponent + interestComponent;
    private String transactionType;
    private double balanceBeforeTransaction;
    private double balanceAfterTransaction;

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
}
