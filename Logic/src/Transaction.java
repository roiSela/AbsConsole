public class Transaction {
    private String transactionId;
    private int transactionTime;
    private double transactionAmount;
    private double fundComponent;
    private double interestComponent;
    //note: transactionAmount = fundComponent + interestComponent;
    private String transactionType;
    private double balanceBeforeTransaction;
    private double balanceAfterTransaction;

    public String toString() {
        String temp = "";
        temp += "The transaction time is: " + getTransactionTime() + '\n';
        temp += "The transaction size is: " + transactionAmount + '\n';
        temp += "The transaction type is " + getTransactionType() + '\n';
        temp += "The balance before transaction " + getBalanceBeforeTransaction() + '\n';
        temp += "The balance before transaction after transaction " + getBalanceAfterTransaction() + '\n';

        return temp;
    }

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
