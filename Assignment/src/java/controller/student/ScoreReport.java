/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import authentication.AuthorizationController;
import dal.ScoreDBContext;
import dal.SubjectDBContext;
import entity.Account;
import entity.Category;
import entity.Point;
import entity.Role;
import entity.Score;
import entity.Subject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author vdhung
 */
public class ScoreReport extends AuthorizationController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account, ArrayList<Role> roles) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, Account account, ArrayList<Role> roles) throws ServletException, IOException {
        int suid = 1;
        if(request.getParameter("suid") != null){
            suid = Integer.parseInt(request.getParameter("suid"));
        }
        ScoreDBContext db = new ScoreDBContext();
        ArrayList<Category> categories = db.getCategoryByStudentIDandSubject(account.getId(), suid);
        request.setAttribute("categories", categories);
        
        ArrayList<Score> scores = db.getScores(account.getId(), suid);
        request.setAttribute("scores", scores);
        
        SubjectDBContext subDB = new SubjectDBContext();
        ArrayList<Subject> subjects = subDB.getSubjectByStudentID(account.getId());
        request.setAttribute("subjects", subjects);
        
        request.getRequestDispatcher("../StudentView/score.jsp").forward(request, response);
    }


}
