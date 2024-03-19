/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecturer;

import authentication.AuthorizationController;
import dal.ScoreDBContext;
import dal.StudentDBContext;
import dal.SubjectDBContext;
import entity.Account;
import entity.Point;
import entity.Role;
import entity.Score;
import entity.Student;
import entity.StudentGroup;
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
@WebServlet(name = "GiveScore", urlPatterns = {"/lecturer/givescore"})
public class GiveScore extends AuthorizationController {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response, Account account, ArrayList<Role> roles) throws ServletException, IOException {
        SubjectDBContext suDB = new SubjectDBContext();
        ArrayList<StudentGroup> groups = suDB.getSubjectByLecturerID(account.getId());
        request.setAttribute("groups", groups);

        String suid_raw = request.getParameter("suid");
        String gid_raw = request.getParameter("gid");
        int suid = Integer.parseInt(suid_raw);
        int gid = Integer.parseInt(gid_raw);

        ScoreDBContext scDB = new ScoreDBContext();
        ArrayList<Point> points = scDB.getPointsBySuid(suid);
        request.setAttribute("points", points);

        StudentDBContext sDB = new StudentDBContext();
        ArrayList<Student> students = sDB.getStudentsByGroupID(gid);
        for (Student student : students) {
            student.setScores(scDB.getScores(student.getId(), suid));
        }
        request.setAttribute("students", students);

        for (Student student : students) {
            for (Score score : student.getScores()) {
                String scorevalue = request.getParameter("score_student" + student.getId() + "_point" + score.getPoint().getId());
                if (scorevalue != null && !scorevalue.isEmpty()) {
                    score.setValue(Double.valueOf(scorevalue));
                    
                }
            }
        }
        
        scDB.deleteScore(gid);
        for (Student student : students) {
            for (Score score : student.getScores()) {
                System.out.println(score.getValue());
            }
            scDB.insertScore(student.getId(), gid, student.getScores());
        }

        response.sendRedirect("givescore?suid=" + suid + "&gid=" + gid);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, Account account, ArrayList<Role> roles) throws ServletException, IOException {

        SubjectDBContext suDB = new SubjectDBContext();
        ArrayList<StudentGroup> groups = suDB.getSubjectByLecturerID(account.getId());
        request.setAttribute("groups", groups);

        String suid_raw = request.getParameter("suid");
        String gid_raw = request.getParameter("gid");
        if (suid_raw != null && !suid_raw.isEmpty() && gid_raw != null && !gid_raw.isEmpty()) {
            int suid = Integer.parseInt(suid_raw);
            int gid = Integer.parseInt(gid_raw);

            ScoreDBContext scDB = new ScoreDBContext();
            ArrayList<Point> points = scDB.getPointsBySuid(suid);
            request.setAttribute("points", points);

            StudentDBContext sDB = new StudentDBContext();
            ArrayList<Student> students = sDB.getStudentsByGroupID(gid);
            for (Student student : students) {
                student.setScores(scDB.getScores(student.getId(), suid));
            }
            request.setAttribute("students", students);

        }

        request.getRequestDispatcher("../LecturerView/givescore.jsp").forward(request, response);
    }

}
