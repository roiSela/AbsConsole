import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
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

                        break;
                    case "4":

                        break;
                    case "5":

                        break;
                    case "6":

                        break;
                    case "7":

                        break;
                    case "8":
                        System.out.println("Exiting the system.");
                        break;
                    default:
                        System.out.print("The option you choose does not exist.");
                        break;
                }
            } catch (Exception e) {
                System.out.print("The option you choose does not exist,");
            }


        }
        input.close();  // Closing Scanner after the use

    }
}
