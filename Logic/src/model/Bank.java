package model;

import generated.*;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;


public class Bank implements BankActions, Serializable {
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

    public Loan getLoanByName(String loanName) {

        for (Loan loan : allTheLoans) {
            if (loan.getLoanName().equals(loanName)) {
                return loan;
            }
        }
        return null;
    }


    private final static String JAXB_XML_GAME_PACKAGE_NAME = "generated";///

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
            return "File is not valid,there is a loan that contains a payment Rate And model.Loan Time Length that do not fit";
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

        for (Customer customer : customers) {//add the loans to the creating customers
            for (Loan loan : allTheLoans) {
                if (customer.getCustomerName().equals(loan.getNameOfCreatingCustomer())) {
                    customer.addCreatedLoan(loan);
                }
            }
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

    @Override //section 3.
    public String getCustomersData() {

        String temp = "";
        for (Customer customer : customers) {
            temp += "The customer name is : " + customer.getCustomerName() + '\n';
            temp += "The customer transactions are :" + '\n';
            temp += customer.getCustomerTransactionsString() + '\n';
            temp += "The Loans that customer invested in are :" + '\n';
            for (String loanId : customer.getIdListOfLoansThatCustomerInvestedIn()) {
                temp += getLoanByName(loanId).toString() + '\n';
            }
            temp += "The Loans that customer borrowed are : " + '\n';
            for (Loan loan : customer.getLoansCustomerCreated()) {
                temp += loan.toString() + '\n';
            }

        }
        return temp;
    }

    @Override //section 4
    public boolean putMoneyInAccount(double moneyToLoad, int customer) {

        Customer ServicedCustomer = customers.get(customer);
        Account accountToAdd = ServicedCustomer.getCustomerAccount();
        accountToAdd.putMoneyInAccount(moneyToLoad, currentTime);
        return true;
    }

    @Override //section 5
    public boolean takeMoneyFromAccount(double moneyToTake, int customer) {

        Customer ServicedCustomer = customers.get(customer);
        Account accountToTake = ServicedCustomer.getCustomerAccount();
        accountToTake.takeMoneyFromAccount(moneyToTake, currentTime);
        return true;
    }

    public String printAllCustomerNames(){

        String allCustomerNames = "";
        int currentCustomer = 1;
        for(Customer customer : customers){
            allCustomerNames += currentCustomer + ". " + customer.getCustomerName() + '\n';
            currentCustomer ++;
        }

        return allCustomerNames;
    }




    @Override//section 6
    public boolean schedulingLoansToCustomer(int customerIndex, double moneyToInvest, List<Loan> loansForScheduling) {
        loansForScheduling = createDeepCopyLoansList(loansForScheduling); //if we don't create a deep copy, the payments will be preformed twice.
        double moneyToInvestCopy = moneyToInvest;
        List<Double> howMuchToInvestInEachLoan = new ArrayList<>();
        List<String> loansForSchedulingNames = new ArrayList<>(); //when we will iterate over the original loans list we will need this
        for (Loan loan : loansForScheduling) {
            loansForSchedulingNames.add(loan.getLoanName());
        }

        //init how much to invest in each loan
        for (int i = 0; i < loansForScheduling.size(); i++) {
            howMuchToInvestInEachLoan.add((double) 0);
        }

        //calculate how much to invest in each loan
        boolean doneDividingMoneyToInvest = false;
        while (!doneDividingMoneyToInvest) {
            double minInvest = findMinMoneyLeftToInvest(loansForScheduling); //find minimum money left to invest in all loans
            if (getNumberOfnewOrPendingLoans(loansForScheduling) == 0) { //no more loans left to invest in. (all are ACTIVE now.)
                doneDividingMoneyToInvest = true;
                break;
            }
            if (moneyToInvest == 0) { //if the investment money ran out
                doneDividingMoneyToInvest = true;
                break;
            }
            if (moneyToInvest >= minInvest * getNumberOfnewOrPendingLoans(loansForScheduling)) { //we will add min invest to all the pending or new loans
                for (int i = 0; i < loansForScheduling.size(); i++) {
                    if (loansForScheduling.get(i).isLoanNewOrPending()) {
                        howMuchToInvestInEachLoan.set(i, howMuchToInvestInEachLoan.get(i) + minInvest);
                        loansForScheduling.get(i).invest(customers.get(customerIndex).getCustomerName(), minInvest);
                        moneyToInvest -= minInvest;
                    }
                }
            } else {// moneyToInvest < minInvest * getNumberOfnewOrPendingLoans(loansForScheduling)
                double moneyToInvestInEachNewOrPendingLoan = moneyToInvest / getNumberOfnewOrPendingLoans(loansForScheduling);
                for (int i = 0; i < loansForScheduling.size(); i++) {
                    if (loansForScheduling.get(i).isLoanNewOrPending()) {
                        howMuchToInvestInEachLoan.set(i, howMuchToInvestInEachLoan.get(i) + moneyToInvestInEachNewOrPendingLoan);
                        loansForScheduling.get(i).invest(customers.get(customerIndex).getCustomerName(), moneyToInvestInEachNewOrPendingLoan);
                        moneyToInvest -= moneyToInvestInEachNewOrPendingLoan;
                    }
                }
                doneDividingMoneyToInvest = true;
                break;
            }

        }

//now we need to implement the changes to the actual loan list, and to update the customer's account:
        int counter = 0;
        for (Loan loan : allTheLoans) {
            if (loansForSchedulingNames.contains(loan.getLoanName())) {
                loan.invest(customers.get(customerIndex).getCustomerName(), howMuchToInvestInEachLoan.get(counter));
                customers.get(customerIndex).getCustomerAccount().takeMoneyFromAccount(howMuchToInvestInEachLoan.get(counter),getCurrentTime());
                customers.get(customerIndex).addInvestedLoan(loan.getLoanName());
                if (loan.isLoanActive()) { //if the loan became active, we need to transfer the loan amount to the loan owner
                        String loanOwner = loan.getNameOfCreatingCustomer();
                        //find the customer who created the loan
                        for (int i = 0; i < customers.size(); i++) {
                            if (customers.get(i).getCustomerName().equals(loanOwner)) {
                                customers.get(i).getCustomerAccount().putMoneyInAccount(loan.getLoanAmount(),getCurrentTime());
                                break;
                            }
                        }
                }
                counter++;
            }
        }
        return true;
    }

    //creat deep copy of loan list
    private List<Loan> createDeepCopyLoansList(List<Loan> loans) {
        List<Loan> deepCopy = new ArrayList<>();
        for (Loan loan : loans) {
            Loan temp = new Loan(loan);
            deepCopy.add(temp);
        }
        return deepCopy;
    }

    //find minimum money left to invest in each loan
    private double findMinMoneyLeftToInvest(List<Loan> loansForScheduling) {
        double minMoneyLeftToInvestInAllTheSchedulingLoans = Double.MAX_VALUE;
        for (Loan loan : loansForScheduling) {
            if (loan.isLoanNewOrPending()) { //if the loan is pending or new
                if (loan.getFundLeftForFinishingLoan() < minMoneyLeftToInvestInAllTheSchedulingLoans) {
                    minMoneyLeftToInvestInAllTheSchedulingLoans = loan.getFundLeftForFinishingLoan();
                }
            }
        }
        return minMoneyLeftToInvestInAllTheSchedulingLoans;
    }

    //get number of active loans
    private int getNumberOfnewOrPendingLoans(List<Loan> loansForScheduling) {
        int numberOfnewOrPendingLoans = 0;
        for (Loan loan : loansForScheduling) {
            if (loan.isLoanNewOrPending()) {
                numberOfnewOrPendingLoans++;
            }
        }
        return numberOfnewOrPendingLoans;
    }

    //utility function for section 6
    public List<Loan> getFilteredLoans(int investingCustomerIndex, double moneyToInvest, Set<String> filteredCategories, double minimumInterestForTimeUnit, int minimumTotalTimeUnitsForInvestment) {

        List<Loan> filteredLoans = new ArrayList<>();
        for (Loan loan : allTheLoans) {
            boolean isLoanNewOrPending = loan.isLoanNewOrPending();
            boolean theLoanIsInFilteredCategories = filteredCategories.contains(loan.getLoanCategory());
            boolean customerIsNotTheLoanOwner = !(loan.getNameOfCreatingCustomer().equals(customers.get(investingCustomerIndex).getCustomerName())); //need to make sure he cannot invest in himself
            boolean theLoanHasEnoughInterest = loan.getInterestPerOneTimeUnit() >= minimumInterestForTimeUnit;
            boolean loanHasEnoughTimeUnits = loan.getTotalAmountOfTimeUnits() >= minimumTotalTimeUnitsForInvestment;

            if (minimumInterestForTimeUnit == -1) {
                theLoanHasEnoughInterest = true;
            }
            if (minimumTotalTimeUnitsForInvestment == -1) {
                loanHasEnoughTimeUnits = true;
            }
            if (filteredCategories.size() == 0) {
                theLoanIsInFilteredCategories = true;
            }
            if (isLoanNewOrPending && theLoanIsInFilteredCategories && customerIsNotTheLoanOwner && theLoanHasEnoughInterest && loanHasEnoughTimeUnits) {
                filteredLoans.add(loan);
            }
        }

        return filteredLoans;
    }

    public List<String> getListOfCustomerNamesAndTheirCurrentBalance() {
        List<String> customerData = new ArrayList<>();
        int customerIndexCounter = 1;
        for (Customer customer : customers) {
            String temp;
            temp = customerIndexCounter + " - " + customer.getCustomerName() + ", balance: " + customer.getCustomerAccount().getCurrentBalance() + '\n';
            customerData.add(temp);
            customerIndexCounter++;
        }
        return customerData;
    }

    public double getCustomerBalance(int customerIndex) {
        return customers.get(customerIndex).getCustomerAccount().getCurrentBalance();
    }

    public boolean isThereAtLeastOneNewOrPendingLoan() {
        for (Loan loan : allTheLoans) {
            if (loan.isLoanNewOrPending()) {
                return true;
            }
        }
        return false;
    }


    @Override //section 7
    public boolean RaiseTheTimeLine() {
        currentTime++;
        for(Customer customer : customers){
            payForLoans(customer);
        }
        return false;
    }

    public void payForLoans(Customer customer){
        customer.setCustomerMessage(null);
        List<Loan> customerUnpaidLoans = customer.getLoansUnpaid();
        for(Loan loan : customerUnpaidLoans){
            loan.setLoanStatus(Loan.LoanStatus.RISK);
        }

        List<Loan> customerLoans = customer.getLoansCustomerCreated();

        for (Loan loan : customerLoans) {
            if(loan.isPaymentDay(currentTime) && loan.getStatus() != Loan.LoanStatus.FINISHED)
                customerUnpaidLoans.add(loan);
        }

        for(Loan loan : customerUnpaidLoans)
        {
            Message newMessage = new Message(loan.getLoanName(), currentTime, loan.getMoneyTopay(currentTime));
            customer.getCustomerMessage().add(newMessage);
        }
    }

    public void payInvestment(double paymentAmmount, Customer customer){
        Transaction paymentToinvestor = new Transaction(paymentAmmount, currentTime, "+", customer.getCustomerAccount().getCurrentBalance(), customer.getCustomerAccount().getCurrentBalance() + paymentAmmount);
        Account customerAccount = customer.getCustomerAccount();
        customerAccount .getCustomerTransactions().add(paymentToinvestor);
        customerAccount .setAccountBalance(customerAccount.getCurrentBalance() + paymentAmmount);

    }

    public void sortLoans( List<Loan> loansToPayToday){

    }



    public List<String> getLoanCategories() {
        return loanCategories;
    }

    public static int getCurrentTime() {
        return currentTime;
    }

    public  Customer getCustomerByName(String customerName){
        for(Customer customer : customers){
            if(customer.getCustomerName().equals(customerName)) {
                return customer;

            }
        }
        return null;
    }


}


