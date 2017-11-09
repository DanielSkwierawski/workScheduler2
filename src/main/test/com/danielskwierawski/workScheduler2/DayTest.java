package com.danielskwierawski.workScheduler2;

import org.junit.Test;

import java.time.LocalDate;

import static com.danielskwierawski.workScheduler2.Plan.defaultWorkTime;
import static org.assertj.core.api.Assertions.assertThat;

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
        int endDayAt = startDayAt + defaultWorkTime;
        // when
        sut = new Day(localDate, startDayAt);
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
}