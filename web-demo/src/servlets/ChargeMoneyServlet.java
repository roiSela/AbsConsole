package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bank;
import model.Customer;
import model.Loan;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "ChargeMoneyServletFunc", urlPatterns = "/ChargeMoneyServlet")
public class ChargeMoneyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Bank bank = ServletUtils.getUserManager(getServletContext());
        String customerName = request.getParameter("customerName");
        String charge = request.getParameter("sumToCharge");

        double chargeDouble = Double.parseDouble(charge);
        bank.putMoneyInAccount(chargeDouble,bank.getCustomerIndex(bank.getCustomerByName(customerName)));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
