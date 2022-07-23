package servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bank;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;

@WebServlet(name = "getBusinessLogic", urlPatterns = "/businessLogic")
public class GetBusinessLogicCopy extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Bank bank = ServletUtils.getUserManager(getServletContext());
        //convert bank to gson
        String bankJson = new Gson().toJson(bank);
        //write bank to response body
        response.getOutputStream().print(bankJson);
        //set ok status
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
