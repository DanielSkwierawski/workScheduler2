package com.danielskwierawski.workScheduler2;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static com.danielskwierawski.workScheduler2.Plan.DEFAULT_WORKING_TIME;
import static com.danielskwierawski.workScheduler2.Plan.MAX_ALLOWED_END_WORKING;
import static com.danielskwierawski.workScheduler2.Plan.MIN_ALLOWED_START_WORKING;

public class Day {
    @Getter
    @Setter
    private LocalDate localDate;
    @Getter
    @Setter
    private Integer start;
    @Getter
    @Setter
    private Integer end;

    public Day(LocalDate localDate) {
        this.localDate = localDate;
    }

    public Day(LocalDate localDate, Integer start) {
        this(localDate, start, start + DEFAULT_WORKING_TIME);
    }

    public Day(LocalDate localDate, Integer start, Integer end) {
        if (start < MIN_ALLOWED_START_WORKING) {
            throw new IllegalArgumentException("Day cannot start before MIN_ALLOWED_START_WORKING(" + MIN_ALLOWED_START_WORKING + ")");
        }
        if (end > MAX_ALLOWED_END_WORKING) {
            throw new IllegalArgumentException("Day cannot end after MAX_ALLOWED_END_WORKING(" + MAX_ALLOWED_END_WORKING + ")");
        }
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
