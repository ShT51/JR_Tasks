package com.javarush.task.task40.task4009;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Locale;

/* 
Buon Compleanno!
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(getWeekdayOfBirthday("30.05.1984", "2015"));
    }

    public static String getWeekdayOfBirthday(String birthday, String year) {
        DateTimeFormatter birthdayFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        LocalDate birthDay = LocalDate.parse(birthday, birthdayFormatter);
        birthDay = birthDay.withYear(Year.parse(year).getValue());

        return birthDay.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
    }
}
