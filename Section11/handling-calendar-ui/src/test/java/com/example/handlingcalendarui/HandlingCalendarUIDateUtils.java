package com.example.handlingcalendarui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HandlingCalendarUIDateUtils {
    public static String convertAriaLabelDateToLocalDateFormat(String dateString) throws ParseException {
        // the desired format is that from the LocalDate, i.e., YYYY-MM-DD
        // the incoming dateString will be of the format "Month day, year" => April 1, 2023

        // gives array [month day, year]
        String[] splitDateOnComma = dateString.split(",");
        // year is the second element in te spit array
        String year = splitDateOnComma[1].trim();

        // split first element in the original array [month day]
        String[] monthAndDay = splitDateOnComma[0].split(" ");

        // month name is the first element, the day of the month is the second element
        String monthName = monthAndDay[0].trim();
        String day = monthAndDay[1].trim();

        day = day.length() == 1 ? "0" + day : day;
        // the code below takes the month name and converts it into a month number
        Date calendarDate = new SimpleDateFormat("MMMM", Locale.ENGLISH).parse(monthName);
        Calendar cal = Calendar.getInstance();
        cal.setTime(calendarDate);
        String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
        month = month.length() == 1 ? "0" + month : month;

        return year + "-" + month + "-" + day;
    }

    public static String extractDayOfMonthFromDate (String dateString) {
        // date format is YYYY-MM-DD
        String day = dateString.split("-")[2];
        String leadingDigit = String.valueOf(day.charAt(0));
        return leadingDigit.equals("0") ? String.valueOf(day.charAt(1)) : day;
    }
}
