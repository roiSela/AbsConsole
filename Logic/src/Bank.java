
import generated.*;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


public class Bank implements BankActions {
    private static int currentTime;
    private List<Customer> customers;
    private List<Loan> allTheLoans;
    private List<String> loanCategories;

    public Bank() {
        currentTime = 1;
        customers = new ArrayList<>();
        allTheLoans = new ArrayList<>();
        loanCategories = new ArrayList<>();
    }

    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";

    @Override
    public String loadSystemDetailsFromFile(String filePath) {

        AbsDescriptor descriptor = null;

        if (!filePath.endsWith("xml")) //check that the file ending is xml
        {
            return "The file specified is not of xml type, please enter the correct file format";
        }
        try {
            InputStream inputStream = new FileInputStream(new File(filePath));
            descriptor = deserializeFrom(inputStream);

        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
            return "Could not find The file";
        }
        //extracting jaxb data
        List<String> absLoanCategories = descriptor.getAbsCategories().getAbsCategory();
        List<AbsCustomer> absCustomers = descriptor.getAbsCustomers().getAbsCustomer();
        List<AbsLoan> absLoans = descriptor.getAbsLoans().getAbsLoan();
        if (!loanCategoriesAreValid(absLoans, absLoanCategories)) {
            return "File is not valid, there is a Referral to a non-existent loan category";
        }
        if (!loanReferralsAreValid(absCustomers, absLoans)) {
            return "File is not valid, there is a Referral to a non-existent customer";
        }
        if (!paymentRateAndLoanTimeLengthFit(absLoans)) {
            return "File is not valid,there is a loan that contains a payment Rate And Loan Time Length that do not fit";
        }

        //if the data is valid,we:
        deleteCurrentBankListsAndInitTime(); //delete the previous system data
        LoadLists(absCustomers, absLoans, absLoanCategories); //update it to the just loaded data.
        return "The file and data were loaded successfully";
    }

    private boolean paymentRateAndLoanTimeLengthFit(List<AbsLoan> absLoans) {
        for (AbsLoan absLoan : absLoans) {
            int totalYazTime = absLoan.getAbsTotalYazTime();
            int paymentRate = absLoan.getAbsPaysEveryYaz();

            if (totalYazTime <= 0 || paymentRate <= 0) {
                return false;
            }

            boolean isDivided = ((totalYazTime % paymentRate) == 0);

            if (!isDivided) {
                return false;
            }
        }
        return true;
    }

    private boolean loanReferralsAreValid(List<AbsCustomer> absCustomers, List<AbsLoan> absLoans) {
        List<String> allCustomerNames = new ArrayList<>();
        for (AbsCustomer customer : absCustomers) {
            allCustomerNames.add(customer.getName());
        }
        for (AbsLoan absLoan : absLoans) {
            if (!allCustomerNames.contains(absLoan.getAbsOwner())) {
                return false;
            }
        }
        return true;
    }

    private boolean loanCategoriesAreValid(List<AbsLoan> absLoans, List<String> absLoanCategories) {
        for (AbsLoan absLoan : absLoans) {
            if (!absLoanCategories.contains(absLoan.getAbsCategory())) {
                return false;
            }
        }
        return true;
    }

    private void deleteCurrentBankListsAndInitTime() {
        customers.clear();
        allTheLoans.clear();
        loanCategories.clear();
        currentTime = 1;

    }

    private void LoadLists(List<AbsCustomer> absCustomers, List<AbsLoan> absLoans, List<String> absLoanCategories) {
        loanCategories = absLoanCategories;
        for (AbsCustomer cust : absCustomers) {
            customers.add(new Customer(cust));
        }
        for (AbsLoan absLoan : absLoans) {
            allTheLoans.add(new Loan(absLoan));
        }
    }

    private static AbsDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(JAXB_XML_GAME_PACKAGE_NAME);
        Unmarshaller u = jc.createUnmarshaller();
        return (AbsDescriptor) u.unmarshal(in);
    }


    @Override //section 2, we will return a list, each string will represent a different loan.
    public List<String> getDataAboutLoansAndTheirStatus() {
        List<String> dataAboutLoans = new ArrayList<>();
        for (Loan loan : allTheLoans) {
            dataAboutLoans.add(loan.toString());
        }
        return dataAboutLoans;
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



    public static int getCurrentTime() {
        return currentTime;
    }
}


