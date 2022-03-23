
import generated.AbsCustomer;

import java.util.*;

public class Customer {
    private String name;
    private Account account;
    private List<String> idListOfLoansThatCustomerInvestedIn; //all the loads that we invested in
    private List<Loan> loansCustomerCreated;

    public Customer(AbsCustomer copyFrom) {
        name = copyFrom.getName();
        account = new Account(copyFrom.getAbsBalance());
        idListOfLoansThatCustomerInvestedIn = new ArrayList<>();
        loansCustomerCreated = new ArrayList<>();
    }
    public String getCustomerTransactionsString(){return account.accountTransactionsToString();}
    public String getCustomerName() {return name;}
    public Account getCustomerAccount() {return account;}
    public List<String> getIdListOfLoansThatCustomerInvestedIn() {return idListOfLoansThatCustomerInvestedIn;}
    public List<Loan> getLoansCustomerCreated() {return loansCustomerCreated;}

}
