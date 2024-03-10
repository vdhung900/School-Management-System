/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.student;

import authentication.AuthenticationController;
import dal.StudentDBContext;
import entity.Account;
import entity.Student;
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
public class ListStudent extends AuthenticationController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, Account account) throws ServletException, IOException {
        int leid = Integer.parseInt(request.getParameter("id"));
        StudentDBContext db = new StudentDBContext();
        ArrayList<Student> students = db.getStudentsByLessonId(leid);
        request.setAttribute("students", students);
        request.getRequestDispatcher("../StudentView/liststudent.jsp").forward(request, response);
    }

}
