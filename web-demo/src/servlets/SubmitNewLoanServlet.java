package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bank;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "SubmitNewLoanServlet", urlPatterns = "/submitNewLoan")
public class SubmitNewLoanServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");

        //get bank
        Bank bank = ServletUtils.getUserManager(getServletContext());

        String customerName = request.getParameter("customerName");
        String loanId = request.getParameter("loanId");
        String category = request.getParameter("category");
        String capital = request.getParameter("capital");
        String totalYaz = request.getParameter("totalYaz");
        String paymentRate = request.getParameter("paymentRate");
        String intrist = request.getParameter("intrist");
        //tell bank to create new loan
        String successOrNot = bank.createNewLoan(customerName, loanId, category, capital, totalYaz, paymentRate, intrist);
        response.getOutputStream().print(successOrNot);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
