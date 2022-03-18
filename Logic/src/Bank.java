
import java.util.*;

public class Bank implements BankActions {
    private static int currentTime;
    private List<Client> clients;
    List<Loan> allTheLoans;

    @Override
    public boolean loadSystemDetailsFromFile(String filePath) {
        return false;
    }

    @Override //section 2, we will return a list, each string will represent a different loan
    public List<String> getDataAboutLoansAndTheirStatus() {
        List<String> dataAboutLoans = new ArrayList<String>();

        for (Loan loan: allTheLoans) {
            String temp = "";
            temp += "The loan name is: " + loan.getLoanName() + '\n'; //the loan identifier
            temp += "The owner of the loan is: " + loan.getNameOfCreatingClient() + '\n'; //owner of the loan
            temp += "The category of the loan is: " + loan.getLoanCategory() + '\n'; //loan category
            temp +="The loan sum is: " + loan.getOriginalLoanAmount() + " ; " + "The original loan time is: " + loan.getTotalAmountOfTimeUnits() + '\n';
            temp += "The interest of the loan: " + loan.getInterestRateInEveryPayment() + " ; " + "The rate of payments: " + loan.getPaymentRatePerTimeUnits()+ '\n';
            temp += "The loan status is: " + loan.getStatus().toString() + '\n';

            switch (loan.getStatus())
            {
                case NEW:
                    break;
                case ACTIVE:
                    break;
                case INRISK:
                    break;
                case PENDING: //we need to add all the Lenders for this loan
                    break;
                case FINISHED:
                    break;
            }
            dataAboutLoans.add(temp);
        }
            return null;
    }

    @Override
    public String getClientsData() {
        return null;
    }

    @Override
    public boolean putMoneyInAccount(int moneyToLoad, int client) {
        return false;
    }

    @Override
    public boolean takeMoneyFromAccount(int moneyToTake, int client) {
        return false;
    }

    @Override
    public boolean SchedulingLoansToClient(int client, double moneyToInvest, List<String> categories, double minimumInterestForTimeUnit, int minimumTotalTimeUnitsForInvestment) {
        return false;
    }

    @Override
    public boolean RaiseTheTimeLine() {
        return false;
    }
}


