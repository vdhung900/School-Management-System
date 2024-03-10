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
public abstract class AuthenticationController extends HttpServlet {
    
    private Account getAuthentication(HttpServletRequest req)
    {
        HttpSession session = req.getSession();
        Account account = (Account)session.getAttribute("account");
        
        if(account==null)
        {
            Cookie[] cookies = req.getCookies();
            if(cookies!=null)
            {
                String username = null;
                String password = null;
                for (Cookie cooky : cookies) {
                    if(cooky.getName().equals("username"))
                        username = cooky.getValue();
                    
                    if(cooky.getName().equals("password"))
                        password = cooky.getValue();
                    
                    if(username != null && password !=null)
                        break;
                }
                if(username == null || password == null)
                    return null;
                else
                {
                    AccountDBContext db = new AccountDBContext();
                    Account test = db.getAccount(username, password);
                    if(test!=null)
                        session.setAttribute("account", test);
                    return test;
                }
            }
        }
        return account ;
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Account account = getAuthentication(req);
        if(account!=null)
        {
            doPost(req, resp, account);
        }
        else
        {
            resp.sendRedirect("login");
        }
    }
    
    
    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp,Account account) 
            throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Account account = getAuthentication(req);
        if(account!=null)
        {
            doGet(req, resp, account);
        }
        else
        {
            resp.sendRedirect("login");
        }
    
    }
    
    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp,Account account) 
            throws ServletException, IOException;
    
}
