package servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Bank;
import utils.ServletUtils;

import java.io.IOException;

@WebServlet(name = "RaiseTimeLine", urlPatterns = "/raiseTheTimeLine")
public class RaiseTheTimeLineServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        Bank bank = ServletUtils.getUserManager(getServletContext());  //get bank
        bank.RaiseTheTimeLine();
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
