package model.classesForTables;

public class MessagesTableObj {

    private String time;
    private String loanName;
    private String paymentAmount;

    public MessagesTableObj(String time, String loanName, String paymentAmount) {
        this.time = time;
        this.loanName = loanName;
        this.paymentAmount = paymentAmount;
    }

    public String getTime() {
        return time;
    }

    public String getLoanName() {
        return loanName;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

}
