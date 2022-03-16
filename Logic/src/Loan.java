import java.util.*;

enum LoanStatus {NEW, PENDING, INRISK, ACTIVE, FINISHED}


public class Loan {

    //Fields that will not change
    String loanName; //every loan has a unique name
    String nameOfCreatingClient; //the client that created this loan
    double originalLoanSum;
    int totalAmoutOfTimeUnits;
    int paymentRatePerTimeUnits; // rate of payment
    double interestRateInEveryPayment;
    String LoanCategory; //info about the loan type

    //dynamic fields
    List<Client> loaners; //change to transaction
    int timeUnitsLeftForFinishingLoan;

    int interestAmountThatWasPaid;
    int interestAmountThatIsLeft;

    int FundPaidSoFar;

    private LoanStatus status;

    public  Loan() //empty ctor
    {

    }
}
