package com.danielskwierawski.workScheduler2;

import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Worker {
    @Getter
    private String name;
    @Getter
    private String surname;
    private Map<LocalDate, Day> dayMap = new HashMap<LocalDate, Day>();

    public Worker(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public void initializeWorkSchedule(LocalDate start, LocalDate end) {
        for (LocalDate current = start; !current.isAfter(end); current = current.plusDays(1)) {
            System.out.println("initializeWorkSchedule(): start:" + start + " current:" + current + " end: " + end);
            dayMap.put(current, new Day(current));
            System.out.println("initializeWorkSchedule(): put:" + current);
        }
    }

    public Map<LocalDate, Day> returnWorkSchedule() {
        return dayMap;
    }

    public void dropWorkSchedule(LocalDate start, LocalDate end) {
        for (LocalDate current = start; !current.isAfter(end); current = current.plusDays(1)) {
            System.out.println("dropWorkSchedule(): start: " + start + " current:" + current + " end:" + end);
            dayMap.remove(current);
            System.out.println("dropWorkSchedule(): remove:" + current);
        }
    }
}
