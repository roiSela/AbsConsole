public class Transaction {
    private static int trancsactionIdCounter = 1;
    private int transactionId;
    private int transactionTime;
    private double transactionAmount;
    private double fundComponent; //it's not a relevant field for transaction class
    private double interestComponent;
    //note: transactionAmount = fundComponent + interestComponent;
    private String transactionType;
    private double balanceBeforeTransaction;
    private double balanceAfterTransaction;

    public Transaction(double transactionAmount,int transactionTime , String transactionType, double balanceBeforeTransaction, double balanceAfterTransaction ){
        this.transactionId = trancsactionIdCounter++;
        this.transactionTime = transactionTime;
        this.transactionAmount = transactionAmount;
        this.balanceBeforeTransaction = balanceBeforeTransaction;
        this.balanceAfterTransaction = balanceAfterTransaction;

    }



    public String toString() {
        String temp = "";
        temp += "The transaction time is: " + getTransactionTime() + '\n';
        temp += "The transaction size is: " + transactionAmount + '\n';
        temp += "The transaction type is " + getTransactionType() + '\n';
        temp += "The balance before transaction " + getBalanceBeforeTransaction() + '\n';
        temp += "The balance before transaction after transaction " + getBalanceAfterTransaction() + '\n';

        return temp;
    }

    private boolean transactionPassedSuccesfully; //sometimes, because of lack of funds from customer, a loan turns to "in-risk" mode, we need to mark this transaction so we can complete it
    //in the future.


    public double getFundComponent() {return fundComponent;} //

    public double getInterestComponent() {return interestComponent;}

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
