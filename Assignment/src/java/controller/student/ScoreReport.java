/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import authentication.AuthenticationController;
import authentication.AuthorizationController;
import dal.ScoreDBContext;
import entity.Account;
import entity.Feature;
import entity.Score;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author vdhung
 */
public class ScoreReport extends AuthorizationController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account, ArrayList<Feature> features) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, Account account, ArrayList<Feature> features) throws ServletException, IOException {
        ScoreDBContext db = new ScoreDBContext();
        ArrayList<Score> scores = db.getScoreByStudentIDandSubject(1, 1);
        request.setAttribute("scores", scores);
        request.getRequestDispatcher("../StudentView/score.jsp").forward(request, response);
    }

}
