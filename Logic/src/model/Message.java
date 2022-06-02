package model;

public class Message {
    String loanName;
    int paymentTime;
    double paymentAmount;

    public Message(String loanName, int paymentTime, double paymentAmount) {
        this.loanName = loanName;
        this.paymentTime = paymentTime;
        this.paymentAmount = paymentAmount;
    }

    public String getLoanName() {
        return loanName;
    }

    public int getPaymentTime() {
        return paymentTime;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }
}
