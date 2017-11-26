package com.danielskwierawski.workScheduler2;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ToString
public class Worker {
    @Getter
    private String name;
    @Getter
    private String surname;
    @Getter
    @JsonSerialize(using = MapSerializer.class)
    private Map<LocalDate, Day> dayMap = new HashMap<LocalDate, Day>();

    public Worker(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public void initializeWorkSchedule(LocalDate start, LocalDate end) {
        for (LocalDate current = start; !current.isAfter(end); current = current.plusDays(1)) {
            dayMap.put(current, new Day());
        }
    }

    public Map<LocalDate, Day> returnWorkSchedule() {
        return dayMap;
    }

    public void dropWorkSchedule(LocalDate start, LocalDate end) {
        for (LocalDate current = start; !current.isAfter(end); current = current.plusDays(1)) {
            dayMap.remove(current);
        }
    }
}
