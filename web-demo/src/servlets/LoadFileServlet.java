package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import kotlin.text.Charsets;
import model.Bank;
import model.Loan;
import utils.ServletUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Scanner;

@WebServlet(name = "fileLoader", urlPatterns = "/loadFile")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
public class LoadFileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Bank bank = ServletUtils.getUserManager(getServletContext());

        Part customerNamePart = request.getPart("customerName");
        //turn customer part to string
        String customerName =readFromInputStream(customerNamePart.getInputStream());
        System.out.println(customerName);

        response.setContentType("text/xml");
        Part filePart = request.getPart("file1");

        bank.loadSystemDetailsFromFile(filePart.getInputStream(), customerName);
    }

    private String readFromInputStream(InputStream inputStream) {
        return new Scanner(inputStream).useDelimiter("\\Z").next();
    }
}
