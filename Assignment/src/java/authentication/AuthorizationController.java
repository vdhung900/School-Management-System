/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package authentication;

import dal.RoleDBContext;
import entity.Account;
import entity.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author vdhung
 */
public abstract class AuthorizationController extends AuthenticationController {

    private ArrayList<Role> getRoles(Account account, HttpServletRequest req) {
        RoleDBContext db = new RoleDBContext();
        String url = req.getServletPath();
        return db.getRoleByUrlAndUsername(account.getUsername(), url);
    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, Account account, ArrayList<Role> roles)
            throws ServletException, IOException;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        ArrayList<Role> roles = getRoles(account, req);
        if (roles.size() < 1) {
            resp.sendRedirect("../LoginView/accessdenied.jsp");
        } else {
            doPost(req, resp, account, roles);
        }
    }

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, Account account, ArrayList<Role> roles)
            throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        ArrayList<Role> roles = getRoles(account, req);
        if (roles.size() < 1) {
            resp.sendRedirect("../LoginView/accessdenied.jsp");
        } else {
            doGet(req, resp, account, roles);
        }
    }
}
