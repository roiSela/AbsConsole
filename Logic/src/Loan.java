import generated.AbsLoan;

import java.util.*;


public class Loan {

    enum LoanStatus {NEW, PENDING, RISK, ACTIVE, FINISHED}

    //Fields that will not change
    private String loanName; //every loan has a unique name
    private String nameOfCreatingClient; //the client that created this loan
    private double loanAmount; //sum of loan
    private int totalAmountOfTimeUnits;
    private int paymentRatePerTimeUnits; // rate of payment
    private double interestRateInEveryPayment;
    private String loanCategory;

    //dynamic fields
    private List<Investment> investments; //list of investing customers and their size of investments.
    private List<Transaction> loanPayments; //the payments that the loan did so far
    private LoanStatus status;

    private int startingTime; //time loan became active
    private int finishingTime; // time loan is finished


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
        loanPayments = new ArrayList<>();

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
                temp += getDataForActiveLoanStatus();
                break;
            case RISK:
                temp += getDataForActiveLoanStatus();
                temp += "The unpaid payments (payment number and sum) are as follows:" + '\n';
                int paymentNumber = 1;
                for (Transaction t: loanPayments) {
                    if (!t.isTransactionPassedSuccesfully()){
                        temp += "Payment number " + paymentNumber + "with sum of " + t.getTransactionAmount() + '\n';
                    }
                    paymentNumber++;
                }
                break;
            case PENDING:
                temp += getDataForPendingLoanStatus();
                break;
            case FINISHED:
                temp += getDataForPendingLoanStatus();
                temp += "The loan became active on time unit: " + getStartingTime() + '\n';
                temp += "The loan was finished on time unit: " + getFinishingTime() + '\n';
                temp += getLoanPaymentsSoFar();
                break;
        }
        return temp;
    }

    //utility function for toString.
    private String getDataForActiveLoanStatus() {
        String temp = "";
        temp += getDataForPendingLoanStatus();
        temp += "The loan became active on time unit: " + getStartingTime() + '\n';
        temp += "Time units left till next payment: " + getTimeUnitsLeftForNextPayment() + '\n';
        temp += getLoanPaymentsSoFar();
        temp += "And as for the interest and fund payments: " + '\n';
        temp += "The amount of fund component that was paid so far: " + getFundPaidSoFar() + '\n';
        temp += "The amount of interest component that was paid so far: " + getInterestPaidSoFar() + '\n';
        temp += "The amount of interest component that's left for finishing the loan: " + getInterestLeftForFinishingLoan() + '\n';
        temp += "The amount of fund component that's left for finishing the loan: " + getFundLeftForFinishingLoan() + '\n';
        return temp;
    }

    //utility function for toString.
    private String getDataForPendingLoanStatus() {
        String temp = "";
        temp += "The loaners for this loan are:\n";
        for (Investment investment : investments) {
            temp += "name: " + investment.getNameOfCustomer() + " and he invested " + investment.getSizeOfInvestment() + '\n';
        }
        temp += "The amount of money raised so far is: " + moneyRaisedSoFar() + '\n';
        double moneyLeftForActiveLoan = loanAmount - moneyRaisedSoFar();
        temp += "And the amount of money left for this loan to become active is: " + moneyLeftForActiveLoan + '\n';
        return temp;
    }

    //utility function for toString.
    private String getLoanPaymentsSoFar() {
        String temp = "";
        temp += "The loan payments so far are as follows: " + '\n';
        int transactionCounter = 1;
        for (Transaction t : loanPayments) {
            temp += "The transection number is: " + transactionCounter + '\n';
            temp += "It happend in time unit: " + t.getTransactionTime() + '\n';
            temp += "Its fund component amount is: " + t.getFundComponent() + '\n';
            temp += "Its interest component amount is: " + t.getInterestComponent() + '\n';
            temp += "Which sums up to a total of: " + t.getTransactionAmount() + '\n';
            transactionCounter++;
        }
        return temp;
    }

    private double getTotalInterestLoanNeedsToPay() {
        return (getLoanAmount() * getInterestRateInEveryPayment());
    }

    private double getFundPaidSoFar() {
        double paidSoFar = 0;
        for (Transaction t : loanPayments) {
            paidSoFar += t.getFundComponent();
        }
        return paidSoFar;
    }

    private double getInterestPaidSoFar() {
        double paidSoFar = 0;
        for (Transaction t : loanPayments) {
            paidSoFar += t.getInterestComponent();
        }
        return paidSoFar;
    }

    private double getInterestLeftForFinishingLoan() {
        return (getTotalInterestLoanNeedsToPay() - getInterestPaidSoFar());
    }

    private double getFundLeftForFinishingLoan() {
        return (loanAmount - getFundPaidSoFar());
    }

    private int getTimeUnitsLeftForNextPayment() {
        int timeLeft = 0;
        int timeOfLastPayment = loanPayments.get(loanPayments.size() - 1).getTransactionTime();
        timeOfLastPayment = timeOfLastPayment % getPaymentRatePerTimeUnits();
        timeLeft = getPaymentRatePerTimeUnits() - timeOfLastPayment;
        return timeLeft;
    }

    public double moneyRaisedSoFar() {
        double total = 0;
        for (Investment investment : investments) {
            total += investment.getSizeOfInvestment();
        }
        return total;
    }

    private int getCurrentTime() {
        return Bank.getCurrentTime();
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

    public List<Transaction> getLoanPayments() {
        return loanPayments;
    }

    public int getStartingTime() {
        return startingTime;
    }

    public int getFinishingTime() {
        return finishingTime;
    }

    public Loan() //empty ctor
    {

    }
}
