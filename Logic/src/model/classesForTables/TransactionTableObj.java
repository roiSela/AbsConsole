package model.classesForTables;

public class TransactionTableObj {
    private String time;


    private String sum;


    private String type;


    private String balanceBefore;


    private String balanceAfter;




    public TransactionTableObj(String time, String sum, String type, String balanceBefore, String balanceAfter) {
        this.time = time;
        this.sum = sum;
        this.type = type;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;

    }

    public String getTime() {return time;}

    public void setTime(String time) {this.time = time;}

    public String getSum() {return sum;}

    public void setSum(String sum) {this.sum = sum;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public String getBalanceBefore() {return balanceBefore;}

    public void setBalanceBefore(String balanceBefore) {this.balanceBefore = balanceBefore;}

    public String getBalanceAfter() {return balanceAfter;}

    public void setBalanceAfter(String balanceAfter) {this.balanceAfter = balanceAfter;}
}
