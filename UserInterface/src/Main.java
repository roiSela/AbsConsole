import java.io.*;
import java.util.*;


public class
        Main {
    // enum MenuOptions {READ_FILE , LOANS_DATA, CUSTOMERS_DATA, LOAD_MONEY, WITHDRAW_MONEY, SCHEDULING, PROMOTING_TIMELINE, EXIT}

    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner input = new Scanner(System.in);
        String running = "-1";
        while (!running.equals("8")) {
            System.out.println("Please enter a whole number between 1 and 8 in order to choose an option.");
            System.out.println("1 - Load system details from file.");
            System.out.println("2 - Show data of all loans.");
            System.out.println("3- Show data of all customers.");
            System.out.println("4 - Load money to customer.");
            System.out.println("5 - withdraw money from customer.");
            System.out.println("6 - Schedule loans for customer.");
            System.out.println("7 - Promote time by one time unit and pay loans.");
            System.out.println("8 - exit the system.");

            // This method reads the number provided using keyboard
            try {
                running = input.nextLine();
                switch (running) {
                    case "1":
                        System.out.println("Please enter the file path:");
                        String filePath = input.nextLine();
                        String validFileRead = bank.loadSystemDetailsFromFile(filePath);
                        System.out.println(validFileRead);
                        break;

                    case "2":
                        System.out.println("The system loans are:");
                        List<String> loansData = bank.getDataAboutLoansAndTheirStatus();
                        for (String loan : loansData) {
                            System.out.println(loan);
                        }
                        break;
                    case "3":
                        System.out.println("The system customers are:");
                        String customerData = bank.getCustomersData();
                        System.out.println(customerData);

                        break;
                    case "4":
                        System.out.println("Choose Customer to load money :");
                        String allCustomerNames = bank.printAllCustomerNames();
                        System.out.println(allCustomerNames);
                        Scanner intInput  = new Scanner(System.in);
                        Integer customerNumber = intInput.nextInt();
                        System.out.println("How much money do you want to load? : ");
                        Scanner doubleInput  = new Scanner(System.in);
                        Double moneyToLoad = doubleInput.nextDouble();
                        bank.putMoneyInAccount(moneyToLoad, customerNumber - 1 );


                        break;
                    case "5":
                        System.out.println("Choose Customer to take money :");
                        allCustomerNames = bank.printAllCustomerNames();
                        System.out.println(allCustomerNames);
                        intInput  = new Scanner(System.in);
                        customerNumber = intInput.nextInt();
                        System.out.println("How much money do you want to take? : ");
                        doubleInput  = new Scanner(System.in);
                        Double moneyToTake = doubleInput.nextDouble();
                        bank.takeMoneyFromAccount(moneyToTake, customerNumber - 1 );

                        break;
                    case "6":
                        bank = matchLoansForClient(bank, input);
                        break;
                    case "7":
                        System.out.println("The current time is: " + Bank.getCurrentTime() +'\n' + "the time after raise is " + (Bank.getCurrentTime() + 1) ) ;
                        bank.RaiseTheTimeLine();


                        break;
                    case "8":
                        System.out.println("Exiting the system.");
                        break;
                    default:
                        System.out.print("The option you choose does not exist.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Could not complete the action an error, the error message is: " + e.toString());
            }


        }
        input.close();  // Closing Scanner after the use

    }

    private static Bank matchLoansForClient(Bank bank, Scanner input) {

        int customerIndexInt = chooseIndexOfInvestingCustomer(bank, input) - 1; //for exapmle, the user put 1 in, but the index of the list start from 0, thus we need to subtract 1
        double investmentAmount = chooseSumToInvest(bank, input, customerIndexInt);
        Set<String> filteredLoanCategories = chooseLoanCategories(bank, input);
        double minimalInterestDouble = chooseMinimalInterest(bank, input);
        int maximalLoanTimeInt = chooseMaximalLoanTime(bank, input);
        List<Loan> matchedLoans = bank.getFilteredLoans(customerIndexInt, investmentAmount, filteredLoanCategories, minimalInterestDouble, maximalLoanTimeInt);
        List<Loan> matchedLoansChosen = chooseMatchedLoans(bank, input, matchedLoans);
        bank.schedulingLoansToCustomer(customerIndexInt, investmentAmount, matchedLoansChosen);
        return bank;
    }

    public static List<Loan> chooseMatchedLoans(Bank bank, Scanner input, List<Loan> matchedLoans) {
        List<Loan> matchedLoansChosen = new ArrayList<>();
        int matchedLoansChosenInt = -1;
        System.out.println("Please choose loans to invest in, one at a time: (press enter,when you are done)");
        for (int i = 0; i < matchedLoans.size(); i++) {
            System.out.println(i + 1 + " - " + matchedLoans.get(i).toString());
        }
        boolean running = true;
        while (running) {
            String loanIndex = input.nextLine();
            if (loanIndex.equals("")) { //the user ended his choice
                if(matchedLoansChosenInt == -1){
                    System.out.println("You did not choose any loan, please try again.");
                }else {
                    running = false;
                }
            } else {//we check if he entered a valid index
                if (isStringInteger(loanIndex)) {
                    if (Integer.parseInt(loanIndex) > 0 && Integer.parseInt(loanIndex) <= matchedLoans.size()) {
                        matchedLoansChosenInt = Integer.parseInt(loanIndex) - 1;
                        matchedLoansChosen.add(matchedLoans.get(matchedLoansChosenInt));
                    } else {
                        System.out.println("The loan index you entered is not in range, please enter a valid loan index.");
                    }
                } else {
                    System.out.println("The loan index you entered is not a number, please enter a valid loan index.");
                }
            }
        }
        return matchedLoansChosen;
    }

    private static double chooseSumToInvest(Bank bank, Scanner input, int customerIndexInt) {
        System.out.println("Please enter the sum you are willing to invest in the loans:");
        boolean validInput = false;
        String sumToInvest = input.nextLine();
        double investmentSum = 0;
        while (!validInput) {
            if (isStringDouble(sumToInvest) && Double.parseDouble(sumToInvest) > 0 && Double.parseDouble(sumToInvest) <= bank.getCustomerBalance(customerIndexInt)) {
                validInput = true;
                investmentSum = Double.parseDouble(sumToInvest);
            } else {
                System.out.println("The sum you entered is not valid or the customer does not have enough money either way, please enter a valid sum.");
                sumToInvest = input.nextLine();
            }
        }
        return investmentSum;
    }

    private static int chooseIndexOfInvestingCustomer(Bank bank, Scanner input) {
        System.out.println("Please choose the number of the customer you wish to match loans for:");
        List<String> customerNamesAndBalance = bank.getListOfCustomerNamesAndTheirCurrentBalance();
        printListOfCustomers(customerNamesAndBalance);
        boolean validInput = false;
        int customerIndexInt = 0;

        while (!validInput) {
            String customerIndex = input.nextLine();
            validInput = true;
            try {
                customerIndexInt = Integer.parseInt(customerIndex);
                //if we reached here that means that the input is an integer,checks that int is in the range of the list
                if (customerIndexInt < 1 || customerIndexInt > customerNamesAndBalance.size()) {
                    System.out.println("The number you entered is not in the range, please try again.");
                    validInput = false;
                }
            } catch (Exception e) {
                System.out.println("The customer index you entered is not a number, please enter a valid index.");
                validInput = false;
            }
        }
        return customerIndexInt;
    }

    private static Set<String> chooseLoanCategories(Bank bank, Scanner input) {
        System.out.println("If you wish, choose a loan category to match the loan for, one at a time (press enter if you are not interested in this filter, or when you're done choosing):");
        List<String> loanCategories = bank.getLoanCategories();
        Set<String> filteredLoanCategories = new HashSet<>();
        printListOfLoanCategories(loanCategories);
        int loanCategoryIndexInt = -1; //if the user does not choose a loan category, we set it to -1, that means that we will match all the loans categories
        boolean running = true;
        while (running) {
            String loanCategoryIndex = input.nextLine();
            if (loanCategoryIndex.equals("")) { //the user ended his choice
                running = false;
            } else {//we check if he entered a valid index
                if (isStringInteger(loanCategoryIndex)) {
                    if (Integer.parseInt(loanCategoryIndex) > 0 && Integer.parseInt(loanCategoryIndex) <= loanCategories.size()) {
                        loanCategoryIndexInt = Integer.parseInt(loanCategoryIndex) - 1;
                        filteredLoanCategories.add(loanCategories.get(loanCategoryIndexInt));
                    } else {
                        System.out.println("The loan category you entered is not in range, please enter a valid loan category.");
                    }
                } else {
                    System.out.println("The loan category you entered is not a number, please enter a valid loan category.");
                }
            }

        }

        if (filteredLoanCategories.size() == 0) {
            System.out.println("You did not choose any loan category, thus we will match all the loan categories.");
        } else {
            System.out.println("You chose the following loan categories:");
            for (String loanCategory : filteredLoanCategories) {
                System.out.println(loanCategory);
            }
        }
        return filteredLoanCategories;
    }

    private static double chooseMinimalInterest(Bank bank, Scanner input) {
        System.out.println("If you wish, choose a minimal interest to match the loan for (press enter if you are not interested in this filter):");
        String minimalInterest = input.nextLine();
        double minimalInterestDouble = -1; //if the user does not choose a minimal interest, we set it to -1, that means that we will match all the loans interests
        if (isStringDouble(minimalInterest)) {
            minimalInterestDouble = Double.parseDouble(minimalInterest);
        }
        return minimalInterestDouble;
    }

    private static int chooseMaximalLoanTime(Bank bank, Scanner input) {
        System.out.println("If you wish, choose a maximal loan time for loan you are willing to invest in (press enter if you are not interested in this filter):");
        String maximalLoanTime = input.nextLine();
        int maximalLoanTimeInt = -1; //if the user does not choose a maximal loan time, we set it to -1, that means that we will match all the loans loan times
        if (isStringInteger(maximalLoanTime)) {
            maximalLoanTimeInt = Integer.parseInt(maximalLoanTime);
        }
        return maximalLoanTimeInt;
    }

    private static boolean isStringDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isStringInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //loan categories print method for the user
    private static void printListOfLoanCategories(List<String> loanCategories) {
        for (int i = 0; i < loanCategories.size(); i++) {
            System.out.println(i + 1 + ". " + loanCategories.get(i));
        }
    }


    private static void printListOfCustomers(List<String> customerNamesAndBalance) {
        for (String customerNameAndBalance : customerNamesAndBalance) {
            System.out.println(customerNameAndBalance);
        }
    }
}

