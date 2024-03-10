/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sonnt
 */
public class DateTimeHelper {
     public static Date getBeginningOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Set the first day of the week (Monday in this case)
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        // Set the calendar to the beginning of the week
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        // Reset hour, minutes, seconds and millis
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
     
    public static Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days); // Adds the specified number of days to the date
        return calendar.getTime();
    }
    
    public static ArrayList<java.sql.Date> toList(java.sql.Date from, java.sql.Date to)
    {
        ArrayList<java.sql.Date> list = new ArrayList<>();
        Date temp = from;
        while(!temp.after(to))
        {
            list.add(convertUtilToSql(temp));
            temp = addDaysToDate(temp, 1);
        }
        return list;
    }
     
    public static java.sql.Date convertUtilToSql(Date utilDate) {
        if (utilDate != null) {
            return new java.sql.Date(utilDate.getTime());
        }
        return null;
    }
    
   public static List<String> getWeeksInYearFormatted(int year) {
        List<String> formattedWeeks = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        Calendar calendar = Calendar.getInstance();

        List<Integer> weeks = getWeeksInYear(year);
        for (Integer week : weeks) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.WEEK_OF_YEAR, week);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Date startDate = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 6);
            Date endDate = calendar.getTime();
            formattedWeeks.add(sdf.format(startDate) + " To " + sdf.format(endDate));
        }

        return formattedWeeks;
    }

    private static List<Integer> getWeeksInYear(int year) {
        List<Integer> weeks = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        while (calendar.get(Calendar.YEAR) == year) {
            int week = calendar.get(Calendar.WEEK_OF_YEAR);
            if (!weeks.contains(week)) {
                weeks.add(week);
            }
            calendar.add(Calendar.DATE, 1);
        }

        return weeks;
    }
    
    public static String getCurrentWeekFormatted() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");
        
        // Get the start date of the current week (Monday)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDate = sdf.format(calendar.getTime());
        
        // Get the end date of the current week (Sunday)
        calendar.add(Calendar.DAY_OF_MONTH, 6);
        String endDate = sdf.format(calendar.getTime());
        
        // Format the week range
        return startDate + " To " + endDate;
    }
}
