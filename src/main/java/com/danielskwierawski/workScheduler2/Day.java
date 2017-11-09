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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day = (Day) o;

        if (start != null ? !start.equals(day.start) : day.start != null) return false;
        if (end != null ? !end.equals(day.end) : day.end != null) return false;
        return localDate.equals(day.localDate);
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + localDate.hashCode();
        return result;
    }
}
