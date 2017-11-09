package com.danielskwierawski.workScheduler2;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class NumberOfDays {
    public Integer getNumberOfDays(LocalDate localDate) {
        int year = localDate.getYear();
        int month = localDate.getMonthValue() - 1;
        int day = localDate.getDayOfMonth();
        Calendar calendar = new GregorianCalendar(year, month, day);
        Integer daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return daysInMonth;
    }
}
