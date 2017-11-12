package com.danielskwierawski.workScheduler2;

import com.danielskwierawski.workScheduler2.Day;
import org.junit.Test;

import java.time.LocalDate;

import static com.danielskwierawski.workScheduler2.Plan.MAX_ALLOWED_END_WORKING;
import static com.danielskwierawski.workScheduler2.Plan.MIN_ALLOWED_START_WORKING;
import static com.danielskwierawski.workScheduler2.Plan.DEFAULT_WORKING_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class DayTest {

    Day sut;

    @Test
    public void createDayOff() throws Exception {
        // given
        LocalDate localDate = LocalDate.of(2017,1,1);
        // when
        sut = new Day(localDate);
        // then
        assertThat(sut.getStart()).isNull();
        assertThat(sut.getEnd()).isNull();
        assertThat(sut.isOff()).isTrue();
    }

    @Test
    public void createStandardWorkingDay() throws Exception {
        // given
        LocalDate localDate = LocalDate.of(2017,1,1);
        int startDayAt = 6;
        int endDayAt = startDayAt + DEFAULT_WORKING_TIME;
        // when
        sut = new Day(localDate, startDayAt);
        // then
        assertThat(sut.getStart()).isEqualTo(startDayAt);
        assertThat(sut.getEnd()).isEqualTo(endDayAt);
        assertThat(sut.isOff()).isFalse();
    }

    @Test
    public void createStandardWorkingDay2() throws Exception {
        // given
        LocalDate localDate = LocalDate.of(2017,1,1);
        int startDayAt = 6;
        int endDayAt = startDayAt + DEFAULT_WORKING_TIME;
        // when
        sut = new Day(localDate, startDayAt, endDayAt);
        // then
        assertThat(sut.getStart()).isEqualTo(startDayAt);
        assertThat(sut.getEnd()).isEqualTo(endDayAt);
        assertThat(sut.isOff()).isFalse();
    }

    @Test
    public void createUnusualWorkingDay() throws Exception {
        // given
        LocalDate localDate = LocalDate.of(2017,1,1);
        int startDayAt = 6;
        int endDayAt = startDayAt + 7;
        // when
        sut = new Day(localDate, startDayAt, endDayAt);
        // then
        assertThat(sut.getStart()).isEqualTo(startDayAt);
        assertThat(sut.getEnd()).isEqualTo(endDayAt);
        assertThat(sut.isOff()).isFalse();
    }

    @Test
    public void createIncorrectWorkingDay_TooEarly() throws Exception {
        // given
        LocalDate localDate = LocalDate.of(2017,1,1);
        int startDayAt = MIN_ALLOWED_START_WORKING -1;
        // when
        try {
            sut = new Day(localDate, startDayAt);
            fail("Should throw an exception if startDay is before MIN_ALLOWED_START_WORKING");
        } catch (Exception e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Day cannot start before MIN_ALLOWED_START_WORKING(" + MIN_ALLOWED_START_WORKING + ")");
        }
        assertThat(sut).isNull();
    }

    @Test
    public void createIncorrectWorkingDay_TooLate() throws Exception {
        // given
        LocalDate localDate = LocalDate.of(2017,1,1);
        int startDayAt = 6;
        int endDayAt = MAX_ALLOWED_END_WORKING + 1;
        // when
        try {
            sut = new Day(localDate, startDayAt, endDayAt);
            fail("Should throw an exception if endDay is after MAX_ALLOWED_END_WORKING");
        } catch (Exception e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Day cannot end after MAX_ALLOWED_END_WORKING(" + MAX_ALLOWED_END_WORKING + ")");
        }
        assertThat(sut).isNull();
    }
}