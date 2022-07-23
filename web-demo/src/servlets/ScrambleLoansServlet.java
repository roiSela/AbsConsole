package servlets;

import clientcomponents.clientscramblebody.ScrambleDTO;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bank;
import model.Loan;
import utils.ServletUtils;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ScrambleLoansServlet", urlPatterns = "/scrambleLoans")
public class ScrambleLoansServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        //get buisness logic
        Bank bank = ServletUtils.getUserManager(getServletContext());

        String scrambleDTOjson = request.getParameter("scrambleDTO");
        Gson gson = new Gson();
        ScrambleDTO scrambleDTO = gson.fromJson(scrambleDTOjson, ScrambleDTO.class);
        int customerIndex = scrambleDTO.getCustomerIndex();
        double moneyToInvest = scrambleDTO.getMoneyToInvest();
        int maxPercentage = scrambleDTO.getMaxPercentage();
        List<Loan> loansToInvestIn = scrambleDTO.getLoansToInvestIn();

        bank.schedulingLoansToCustomer(customerIndex, moneyToInvest, loansToInvestIn, maxPercentage);
        //now loans to invest in is updated
        ScrambleDTO scrambleDTOForResponse = new ScrambleDTO(customerIndex, moneyToInvest, maxPercentage, loansToInvestIn);
        String scrambleDTOForResponseJson = gson.toJson(scrambleDTOForResponse);
        response.getOutputStream().print(scrambleDTOForResponseJson);
        response.setStatus(HttpServletResponse.SC_OK);
    }


}
