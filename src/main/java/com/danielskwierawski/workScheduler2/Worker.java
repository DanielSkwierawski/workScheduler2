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
}
