import generated.AbsLoan;

import java.util.*;


public class Loan {

    enum LoanStatus {NEW, PENDING, RISK, ACTIVE, FINISHED}

    //Fields that will not change
    private String loanName; //every loan has a unique name
    private String nameOfCreatingCustomer; //the client that created this loan
    private double loanAmount; //sum of loan
    private int totalAmountOfTimeUnits;
    private int paymentRatePerTimeUnits; // rate of payment, for example, every 3 time units.
    private double interestRateInEveryPayment;
    private String loanCategory;


    //dynamic fields
    private List<Investment> investments; //list of investing customers and their size of investments.
    private List<Transaction> loanPayments; //the payments that the loan did so far
    private LoanStatus status;
    private double accumalatedDebt = 0; ///the total debt acculumated to this moment

    private int startingTime; //time loan became active
    private int finishingTime; // time loan is finished

    public boolean isPaymentDay(int currentTime) {
        boolean isPaymentTime = (currentTime - startingTime) % paymentRatePerTimeUnits == 0;
        boolean isLastPayment = currentTime == startingTime + totalAmountOfTimeUnits;
        return isPaymentTime || isLastPayment;
    }


    public Loan(AbsLoan copyFrom) {
        loanName = copyFrom.getId();
        nameOfCreatingCustomer = copyFrom.getAbsOwner();
        loanAmount = copyFrom.getAbsCapital();
        totalAmountOfTimeUnits = copyFrom.getAbsTotalYazTime();
        paymentRatePerTimeUnits = copyFrom.getAbsPaysEveryYaz();
        interestRateInEveryPayment = copyFrom.getAbsIntristPerPayment();
        loanCategory = copyFrom.getAbsCategory();
        status = LoanStatus.NEW;
        investments = new ArrayList<>();
        loanPayments = new ArrayList<>();

    }

    public Loan(Loan copyFrom) {
        loanName = copyFrom.getLoanName();
        nameOfCreatingCustomer = copyFrom.getNameOfCreatingCustomer();
        loanAmount = copyFrom.getLoanAmount();
        totalAmountOfTimeUnits = copyFrom.getTotalAmountOfTimeUnits();
        paymentRatePerTimeUnits = copyFrom.getPaymentRatePerTimeUnits();
        interestRateInEveryPayment = copyFrom.getInterestRateInEveryPayment();
        loanCategory = copyFrom.getLoanCategory();
        status = copyFrom.getStatus();

        investments = new ArrayList<>();
        List<Investment> copyFromInvestments = copyFrom.getInvestments();
        for (Investment i : copyFromInvestments) {
            investments.add(new Investment(i.getNameOfCustomer(), i.getSizeOfInvestment()));
        }
        loanPayments = new ArrayList<>();
        List<Transaction> copyFromLoanPayments = copyFrom.getLoanPayments();
        int counter = 1;
        for (Transaction t : copyFromLoanPayments) {
            loanPayments.add(new Transaction(counter, t.getTransactionAmount(), t.getFundComponent(), t.getInterestComponent(), t.getTransactionTime(), t.isTransactionPassedSuccesfully()));
            counter++;
        }

        startingTime = copyFrom.getStartingTime();
        finishingTime = copyFrom.getFinishingTime();
    }


    @Override
    public String toString() {
        String temp = "";
        temp += "The loan name is: " + getLoanName() + '\n'; //the loan identifier
        temp += "The owner of the loan is: " + getNameOfCreatingCustomer() + '\n'; //owner of the loan
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
                for (Transaction t : loanPayments) {
                    if (!t.isTransactionPassedSuccesfully()) {
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
            temp += "name: " + investment.getNameOfCustomer() + " invested: " + investment.getSizeOfInvestment() + '\n';
        }
        temp += "The amount of money raised so far is: " + moneyRaisedSoFar() + '\n';
        double moneyLeftForActiveLoan = loanAmount - moneyRaisedSoFar();
        temp += "And the amount of money left for this loan to become active is: " + moneyLeftForActiveLoan + '\n';
        return temp;
    }

    //utility function for toString.
    private String getLoanPaymentsSoFar() {
        if (loanPayments.size() == 0) {
            return "There are no payments for this loan yet.";
        } else {
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
    }

    public void invest(String nameOfInvestor, double amountOfInvestment) {
        Boolean isCustomerAlreadyInvested = false;
        for (Investment i : investments) {
            if (i.getNameOfCustomer().equals(nameOfInvestor)) {
                isCustomerAlreadyInvested = true;
                i.addInvestment(amountOfInvestment);
                break;
            }
        }
        if (!isCustomerAlreadyInvested) {
            investments.add(new Investment(nameOfInvestor, amountOfInvestment));
        }
        updateLoanStatus();
    }

    //update loan status
    public void updateLoanStatus() {
        if (moneyRaisedSoFar() >= 0) {
            status = LoanStatus.PENDING;
        }
        if (moneyRaisedSoFar() >= loanAmount) {
            status = LoanStatus.ACTIVE;
            startingTime = getCurrentTime();
        }
        if (thereIsAnUnpaidLoanPayment()) {
            status = LoanStatus.RISK;
        }
        if (getFundLeftForFinishingLoan() <= 0 && getInterestLeftForFinishingLoan() <= 0) {
            status = LoanStatus.FINISHED;
            finishingTime = getCurrentTime();
        }

    }

    boolean thereIsAnUnpaidLoanPayment() {
        for (Transaction t : loanPayments) {
            if (t.isTransactionPassedSuccesfully() == false) {
                return true;
            }
        }
        return false;
    }

    private double getTotalInterestLoanNeedsToPay() {
        return (getLoanAmount() * (getInterestRateInEveryPayment() / 100));
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

    public double getFundLeftForFinishingLoan() {
        return (loanAmount - getFundPaidSoFar());
    }

    private int getTimeUnitsLeftForNextPayment() {
        int timeLeft = 0;
        if (loanPayments.size() > 0) {
            int timeOfLastPayment = loanPayments.get(loanPayments.size() - 1).getTransactionTime();
            timeOfLastPayment = timeOfLastPayment % getPaymentRatePerTimeUnits();
            timeLeft = getPaymentRatePerTimeUnits() - timeOfLastPayment;
            return timeLeft;
        } else //the loan just became active
            return getPaymentRatePerTimeUnits();
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

    public String getNameOfCreatingCustomer() {
        return nameOfCreatingCustomer;
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

    public double getInterestPerOneTimeUnit() { //for filtering loans by interest per one time unit in section 6
        double interestPerOneTimeUnit = getInterestRateInEveryPayment() / getPaymentRatePerTimeUnits();
        return interestPerOneTimeUnit;
    }
    public void setAccumalatedDebt(double accumalatedDebt){this.accumalatedDebt = accumalatedDebt;}
    public void setLoanStatus(LoanStatus status){this.status = status;}


    public boolean isLoanNewOrPending() {
        return status == LoanStatus.NEW || status == LoanStatus.PENDING;
    }

    public boolean isLoanActive() {
        if (status == LoanStatus.ACTIVE) {
            return true;
        }
        return false;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public Loan() //empty ctor
    {

    }

    public int getTotalAmountOfPayments() {
        if ((totalAmountOfTimeUnits) % paymentRatePerTimeUnits == 0) {
            return (totalAmountOfTimeUnits) / paymentRatePerTimeUnits;
        } else {
            return (totalAmountOfTimeUnits) / paymentRatePerTimeUnits + 1;
        }
    }

    public double calculateMoneyToPay(int currentTime) {
        double moneyToPay;
        double fundComponent = loanAmount / getTotalAmountOfPayments();
        double interestComponent = fundComponent * interestRateInEveryPayment / 100;
        double regularPayment = fundComponent + interestComponent + accumalatedDebt;

        if (currentTime > startingTime + totalAmountOfTimeUnits) {
            moneyToPay = accumalatedDebt;
        }
        else {
            moneyToPay = regularPayment;
        }
        return moneyToPay;

    }





    public void updateDebt(double newDebt)
    {
        status = LoanStatus.RISK;
        accumalatedDebt = newDebt;
    }
}
