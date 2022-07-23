package clientcomponents.clientscramblebody;

import model.Loan;

import java.util.List;

public class ScrambleDTO {
    int customerIndex;
    double moneyToInvest;
    int maxPercentage;
    List<Loan> loansToInvestIn;

    public int getCustomerIndex() {
        return customerIndex;
    }

    public double getMoneyToInvest() {
        return moneyToInvest;
    }

    public int getMaxPercentage() {
        return maxPercentage;
    }

    public List<Loan> getLoansToInvestIn() {
        return loansToInvestIn;
    }

    public ScrambleDTO(int customerIndex, double moneyToInvest, int maxPercentage, List<Loan> loansToInvestIn) {
        this.customerIndex = customerIndex;
        this.moneyToInvest = moneyToInvest;
        this.maxPercentage = maxPercentage;
        this.loansToInvestIn = loansToInvestIn;
    }


}
