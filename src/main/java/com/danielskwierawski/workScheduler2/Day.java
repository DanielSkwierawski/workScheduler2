package com.danielskwierawski.workScheduler2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

import static com.danielskwierawski.workScheduler2.Plan.DEFAULT_WORKING_TIME;
import static com.danielskwierawski.workScheduler2.Plan.MAX_ALLOWED_END_WORKING;
import static com.danielskwierawski.workScheduler2.Plan.MIN_ALLOWED_START_WORKING;

@EqualsAndHashCode
@ToString
public class Day {
    @Getter
    @Setter
    private Integer start;
    @Getter
    @Setter
    private Integer end;

    public Day() {
    }

    public Day(Integer start) {
        this(start, start + DEFAULT_WORKING_TIME);
    }

    public Day(Integer start, Integer end) {
        if (start < MIN_ALLOWED_START_WORKING) {
            throw new IllegalArgumentException("Day cannot start before MIN_ALLOWED_START_WORKING(" + MIN_ALLOWED_START_WORKING + ")");
        }
        if (end > MAX_ALLOWED_END_WORKING) {
            throw new IllegalArgumentException("Day cannot end after MAX_ALLOWED_END_WORKING(" + MAX_ALLOWED_END_WORKING + ")");
        }
        this.start = start;
        this.end = end;
    }

    @JsonIgnore
    public boolean isOff() {
        if (this.start == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean up1() {
        if (end != null && end == MAX_ALLOWED_END_WORKING) {
            return false;
        }
        if (start == null) {
            start = MIN_ALLOWED_START_WORKING;
            end = start + DEFAULT_WORKING_TIME;
            return true;
        }
        start++;
        end++;
        return true;
    }
}
