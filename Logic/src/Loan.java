import java.util.*;




public class Loan {

    enum LoanStatus {NEW, PENDING, INRISK, ACTIVE, FINISHED}

    //Fields that will not change
    private String loanName; //every loan has a unique name
    private String nameOfCreatingClient; //the client that created this loan
    private double originalLoanAmount; //sum of loan
    private int totalAmountOfTimeUnits;
    private int paymentRatePerTimeUnits; // rate of payment
    private double interestRateInEveryPayment;
    private String LoanCategory; //info about the loan type

    //dynamic fields
    private List<Investment> investments;
    private LoanStatus status;
    private double currentLoanAmount; //money raised so far. when currentLoadAmount=originalLoanAmount the loan turns from pending to active

    //private int timeUnitsLeftForFinishingLoan; probably will become a function

    //private double interestAmountThatWasPaid; probably will become a function
    // private double interestAmountThatIsLeft; probably will become a function

    // private int FundPaidSoFar; probably will become a function


    public String getLoanName() {
        return loanName;
    }

    public String getNameOfCreatingClient() {
        return nameOfCreatingClient;
    }

    public double getOriginalLoanAmount() {
        return originalLoanAmount;
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
        return LoanCategory;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public Loan() //empty ctor
    {

    }
}
