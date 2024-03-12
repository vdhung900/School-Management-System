/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package authentication;

import dal.AccountDBContext;
import entity.Account;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author vdhung
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("LoginView/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        Cookie c_username = new Cookie("username", username);
        Cookie c_password = new Cookie("password", password);
        
        if (remember != null) {
                c_username.setMaxAge(3600 * 24 * 7);
                c_password.setMaxAge(3600 * 24 * 7);
            }else{
                c_username.setMaxAge(0);
                c_password.setMaxAge(0);
            }

        AccountDBContext db = new AccountDBContext();
        Account account = db.getAccount(username, password);
        
        if (account != null) {  
            response.addCookie(c_username);
            response.addCookie(c_password);
            
            HttpSession session = request.getSession();
            session.setAttribute("account", account);
            response.sendRedirect("home");
        } else {
            String invalid_statement= "Invalid name or password!!!";
            request.setAttribute("invalid", invalid_statement);
            request.getRequestDispatcher("LoginView/login.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
