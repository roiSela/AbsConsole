import generated.AbsLoan;

import java.util.*;


public class Loan {

    enum LoanStatus {NEW, PENDING, INRISK, ACTIVE, FINISHED}

    //Fields that will not change
    private String loanName; //every loan has a unique name
    private String nameOfCreatingClient; //the client that created this loan
    private double loanAmount; //sum of loan
    private int totalAmountOfTimeUnits;
    private int paymentRatePerTimeUnits; // rate of payment
    private double interestRateInEveryPayment;
    private String loanCategory;

    //dynamic fields
    private List<Investment> investments;
    private LoanStatus status;

    //private int timeUnitsLeftForFinishingLoan; probably will become a function

    //private double interestAmountThatWasPaid; probably will become a function
    // private double interestAmountThatIsLeft; probably will become a function

    // private int FundPaidSoFar; probably will become a function

    public Loan(AbsLoan copyFrom) {
        loanName = copyFrom.getId();
        nameOfCreatingClient = copyFrom.getAbsOwner();
        loanAmount = copyFrom.getAbsCapital();
        totalAmountOfTimeUnits = copyFrom.getAbsTotalYazTime();
        paymentRatePerTimeUnits = copyFrom.getAbsPaysEveryYaz();
        interestRateInEveryPayment = copyFrom.getAbsIntristPerPayment();
        loanCategory = copyFrom.getAbsCategory();
        status = LoanStatus.NEW;
        investments = new ArrayList<>();
    }

    @Override
    public String toString() {
        String temp = "";
        temp += "The loan name is: " + getLoanName() + '\n'; //the loan identifier
        temp += "The owner of the loan is: " + getNameOfCreatingClient() + '\n'; //owner of the loan
        temp += "The category of the loan is: " + getLoanCategory() + '\n'; //loan category
        temp += "The loan sum is: " + getLoanAmount() + " ; " + "The original loan time is: " + getTotalAmountOfTimeUnits() + '\n';
        temp += "The interest of the loan: " + getInterestRateInEveryPayment() + " ; " + "The rate of payments: " + getPaymentRatePerTimeUnits() + '\n';
        temp += "The loan status is: " + getStatus().toString() + '\n';

        switch (status) {
            case NEW:
                break;
            case ACTIVE:
                break;
            case INRISK:
                break;
            case PENDING:
                temp += "The loaners for this loan are:\n";
                for (Investment investment : investments) {
                    temp += "name: " + investment.getNameOfCustomer() + " and he invested " + investment.getSizeOfInvestment() + '\n';
                }
                temp += "The amount of money raised so far is: " + moneyRaisedSoFar() +'\n';
                double moneyLeftForActiveLoan = loanAmount - moneyRaisedSoFar();
                temp += "And the amount of money left for this loan to become active is: " + moneyLeftForActiveLoan + '\n';

                break;
            case FINISHED:
                break;
        }
        return temp;
    }

    public double moneyRaisedSoFar() {
        double total = 0;
        for (Investment investment : investments) {
            total += investment.getSizeOfInvestment();
        }
        return total;
    }

    public String getLoanName() {
        return loanName;
    }

    public String getNameOfCreatingClient() {
        return nameOfCreatingClient;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public int getTotalAmountOfTimeUnits() {
        return totalAmountOfTimeUnits;
    }

    public int getPaymentRatePerTimeUnits() {
        return paymentRatePerTimeUnits;
    }

    public double getInterestRateInEveryPayment() {
        return interestRateInEveryPayment;
    }

    public String getLoanCategory() {
        return loanCategory;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public Loan() //empty ctor
    {

    }
}
