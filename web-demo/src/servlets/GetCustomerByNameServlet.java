package servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bank;
import model.Customer;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "getCustomerByNameServlet", urlPatterns = "/customerByName")
public class GetCustomerByNameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Bank bank = ServletUtils.getUserManager(getServletContext());
        String customerName = request.getParameter("customerName");
        Customer customer = bank.getCustomerByName(customerName);
        if (customer == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            String customerJson = new Gson().toJson(customer);
            response.getOutputStream().print(customerJson);
            response.setStatus(HttpServletResponse.SC_OK);
        }

    }
}
