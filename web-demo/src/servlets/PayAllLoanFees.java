package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "PayAllLoanFeesFunc", urlPatterns = "/PayAllLoanFees")
public class PayAllLoanFees extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Bank bank = ServletUtils.getUserManager(getServletContext());
        String customerName = request.getParameter("customerName");
        String loanId = request.getParameter("loanId");
        Customer customer = bank.getCustomerByName(customerName);
        Loan currentLoan = bank.getLoanById(loanId);

        double paymentAmmount = currentLoan.paymentAmountToFinishTheLoan();
        customer.removeFromLoansUnpaid(currentLoan);
        int currentTime = Bank.getCurrentTime();
        Transaction paymentToinvestor = new Transaction(paymentAmmount, currentTime, "+", customer.getCustomerAccount().getCurrentBalance(), customer.getCustomerAccount().getCurrentBalance() - paymentAmmount);
        currentLoan.getLoanPayments().add(paymentToinvestor);
        Account customerAccount = customer.getCustomerAccount();
        customerAccount.getCustomerTransactions().add(paymentToinvestor);
        customerAccount.setAccountBalance(customerAccount.getCurrentBalance() - paymentAmmount);
        currentLoan.updateLoanPayedSoFar(paymentAmmount);
        currentLoan.setLoanStatusToFinished();

        for (Investment investment : currentLoan.getInvestments()) {
            double investmentPart = investment.getSizeOfInvestment() / currentLoan.getLoanAmount();
            double payment = paymentAmmount * investmentPart;
            Customer investedCustomer = bank.getCustomerByName(investment.getNameOfCustomer());
            bank.payInvestment(paymentAmmount, investedCustomer);
        }
        currentLoan.setAccumalatedDebt(0);

    }
}
