/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package authentication;

import dal.FeatureDBContext;
import entity.Account;
import entity.Feature;
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

    private ArrayList<Feature> getFeatures(Account account, HttpServletRequest req) {
        FeatureDBContext db = new FeatureDBContext();
        String url = req.getServletPath();
        System.out.println("sonnt:" + url);
        return db.getByUsernameAndUrl(account.getUsername(), url);
    }

    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp, Account account, ArrayList<Feature> features)
            throws ServletException, IOException;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        ArrayList<Feature> features = getFeatures(account, req);
        if (features.size() < 1) {
            resp.getWriter().println("access denied!");
        } else {
            doPost(req, resp, account, features);
        }
    }

    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp, Account account, ArrayList<Feature> features)
            throws ServletException, IOException;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        ArrayList<Feature> features = getFeatures(account, req);
        if (features.size() < 1) {
            resp.getWriter().println("access denied!");
        } else {
            doGet(req, resp, account, features);
        }
    }

}
