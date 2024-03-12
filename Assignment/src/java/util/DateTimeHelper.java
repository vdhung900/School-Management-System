/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author vdhung
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
    
    public static List<String> getWeeksFromFirstMonday(String y) {
        int year = Integer.parseInt(y);
        List<String> weeks = new ArrayList<>();
        LocalDate firstMondayOfYear = LocalDate.of(year, 1, 1).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));
        LocalDate lastDayOfYear = LocalDate.of(year, 12, 31);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");

        LocalDate startOfWeek = firstMondayOfYear;
        LocalDate endOfWeek = startOfWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        while (startOfWeek.isBefore(lastDayOfYear) || (startOfWeek.equals(lastDayOfYear) && endOfWeek.getDayOfWeek() != DayOfWeek.MONDAY)) {

            weeks.add(startOfWeek.format(formatter) + " To " + endOfWeek.format(formatter));

            startOfWeek = startOfWeek.plusWeeks(1);
            endOfWeek = startOfWeek.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        }

        return weeks;
    }

    public static List<LocalDate> getDaysOfWeek(String y, String w) {
        int year = Integer.parseInt(y);
        int weekNumber = Integer.parseInt(w);
        List<LocalDate> dayOfWeek = new ArrayList<>();
        LocalDate date = LocalDate.now().withYear(year).with(WeekFields.ISO.weekOfYear(), weekNumber).with(DayOfWeek.MONDAY); // Set to Monday of the given week
        for (int i = 0; i < 7; i++) {
            dayOfWeek.add(date); // Format the date before adding to the list
            date = date.plusDays(1);
        }
        return dayOfWeek;
    }

    public static LocalDate[] getWeek(String y, String w) {
        int year = Integer.parseInt(y);
        int weekNumber = Integer.parseInt(w);
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
        LocalDate firstDayOfFirstWeek = firstDayOfYear.with(weekFields.weekOfYear(), 1);

        LocalDate startOfWeek = firstDayOfFirstWeek.plusWeeks(weekNumber - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return new LocalDate[]{startOfWeek, endOfWeek};
    }
}
