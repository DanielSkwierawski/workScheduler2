package com.danielskwierawski.workScheduler2;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static com.danielskwierawski.workScheduler2.Plan.defaultWorkTime;

public class Day {
    @Getter
    @Setter
    private Integer start;
    @Getter
    @Setter
    private Integer end;
    @Getter
    @Setter
    private LocalDate localDate;

    public Day(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Day(LocalDate localDate, Integer start) {
        this.localDate = localDate;
        this.start = start;
        this.end = start + defaultWorkTime;
    }

    public Day(LocalDate localDate, Integer start, Integer end) {
        this.localDate = localDate;
        this.start = start;
        this.end = end;
    }

    public boolean isOff() {
        if (this.start == null) {
            return true;
        } else {
            return false;
        }
    }
}
