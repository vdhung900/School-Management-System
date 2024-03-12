/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.lecturer;

import authentication.AuthenticationController;
import authentication.AuthorizationController;
import dal.LessonDBContext;
import dal.TimeSlotDBContext;
import entity.Account;
import entity.Feature;
import entity.Lesson;
import entity.TimeSlot;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import util.DateTimeHelper;
import static util.DateTimeHelper.getDaysOfWeek;
import static util.DateTimeHelper.getWeek;
import static util.DateTimeHelper.getWeeksFromFirstMonday;

/**
 *
 * @author vdhung
 */
public class Schedule extends AuthorizationController {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp, Account account, ArrayList<Feature> features) throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response, Account account, ArrayList<Feature> features) throws ServletException, IOException {
HttpSession session = request.getSession();

        String year = request.getParameter("year");
        String week = request.getParameter("week");
        
         int id = Integer.parseInt(request.getParameter("id"));
         request.setAttribute("id", id);

        if (year == null || week == null) {
            year = String.valueOf(LocalDate.now().getYear());
            week = String.valueOf(LocalDate.now().get(WeekFields.ISO.weekOfWeekBasedYear()));
        }
        List<String> years = new ArrayList<>();
        years.add("2021");years.add("2022");years.add("2023"); years.add("2024");years.add("2025");
        session.setAttribute("years", years);

        TimeSlotDBContext timeDB = new TimeSlotDBContext();
        ArrayList<TimeSlot> slots = timeDB.list();
        session.setAttribute("slots", slots);

        session.setAttribute("year", year);
        session.setAttribute("week", week);
        LocalDate[] weekdate = getWeek(year, week);

        LessonDBContext lessDB = new LessonDBContext();
        ArrayList<Lesson> lessons = lessDB.getLessonByDateLecturer(id, weekdate[0], weekdate[1]);
        session.setAttribute("lessons", lessons);

        List<LocalDate> dayOfWeek = getDaysOfWeek(year, week);
        session.setAttribute("dayOfWeek", dayOfWeek);

        List<String> weeks = getWeeksFromFirstMonday(year);
        session.setAttribute("weeks", weeks);
        
        request.getRequestDispatcher("../LecturerView/lecschedule.jsp").forward(request, response);    }

}
