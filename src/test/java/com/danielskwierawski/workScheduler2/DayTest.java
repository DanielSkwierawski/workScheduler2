package com.danielskwierawski.workScheduler2;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static com.danielskwierawski.workScheduler2.Plan.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class DayTest {

    Day sut;

    @Test
    public void createDayOff() throws Exception {
        // given
        // when
        sut = new Day();
        // then
        assertThat(sut.getStart()).isNull();
        assertThat(sut.getEnd()).isNull();
        assertThat(sut.isOff()).isTrue();
    }

    @Test
    public void createStandardWorkingDay() throws Exception {
        // given
        int startDayAt = 6;
        int endDayAt = startDayAt + DEFAULT_WORKING_TIME;
        // when
        sut = new Day(startDayAt);
        // then
        assertThat(sut.getStart()).isEqualTo(startDayAt);
        assertThat(sut.getEnd()).isEqualTo(endDayAt);
        assertThat(sut.isOff()).isFalse();
    }

    @Test
    public void createStandardWorkingDay2() throws Exception {
        // given
        int startDayAt = 6;
        int endDayAt = startDayAt + DEFAULT_WORKING_TIME;
        // when
        sut = new Day(startDayAt, endDayAt);
        // then
        assertThat(sut.getStart()).isEqualTo(startDayAt);
        assertThat(sut.getEnd()).isEqualTo(endDayAt);
        assertThat(sut.isOff()).isFalse();
    }

    @Test
    public void createUnusualWorkingDay() throws Exception {
        // given
        int startDayAt = 6;
        int endDayAt = startDayAt + 7;
        // when
        sut = new Day(startDayAt, endDayAt);
        // then
        assertThat(sut.getStart()).isEqualTo(startDayAt);
        assertThat(sut.getEnd()).isEqualTo(endDayAt);
        assertThat(sut.isOff()).isFalse();
    }

    @Test
    public void createIncorrectWorkingDay_TooEarly() throws Exception {
        // given
        int startDayAt = MIN_ALLOWED_START_WORKING - 1;
        // when
        try {
            sut = new Day(startDayAt);
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
        int startDayAt = 6;
        int endDayAt = MAX_ALLOWED_END_WORKING + 1;
        // when
        try {
            sut = new Day(startDayAt, endDayAt);
            fail("Should throw an exception if endDay is after MAX_ALLOWED_END_WORKING");
        } catch (Exception e) {
            assertThat(e)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Day cannot end after MAX_ALLOWED_END_WORKING(" + MAX_ALLOWED_END_WORKING + ")");
        }
        assertThat(sut).isNull();
    }

    @Test
    public void modifyWorkingHours1Parameter() throws Exception {
        // given
        Day sut = new Day(6);
        // when
        sut.setDay(14);
        // then
        assertThat(sut.getStart()).isEqualTo(14);
        assertThat(sut.getEnd()).isEqualTo(22);
    }

    @Test
    public void modifyWorkingHours2Parameters() throws Exception {
        // given
        Day sut = new Day(6);
        // when
        sut.setDay(14, 21);
        // then
        assertThat(sut.getStart()).isEqualTo(14);
        assertThat(sut.getEnd()).isEqualTo(21);
    }

    @Test
    public void checkUp1() throws Exception {
        // given
        Day day1 = new Day();
        Day day2 = new Day(6);
        Day day3 = new Day(6, 12);
        Day day4 = new Day(14);
        Day day5 = new Day(16, 22);
        // when
        day1.up1();
        day2.up1();
        day3.up1();
        boolean boolean4 = day4.up1();
        boolean boolean5 = day5.up1();
        // then
        assertThat(day1.isOff()).isFalse();
        assertThat(day1.getStart()).isEqualTo(6);
        assertThat(day1.getEnd()).isEqualTo(14);
        assertThat(day2.getStart()).isEqualTo(7);
        assertThat(day2.getEnd()).isEqualTo(15);
        assertThat(day3.getStart()).isEqualTo(7);
        assertThat(day3.getEnd()).isEqualTo(13);
        assertThat(boolean4).isFalse();
        assertThat(boolean5).isFalse();

    }

    @Test
    public void checkDayToJson() throws Exception {
        // given
        Day dayOff = new Day();
        String expectedJsonDayOff = "{\"start\":null,\"end\":null}";
        Day standardWorkingDay = new Day(6);
        String expectedJsonStandardWorkingDay = "{\"start\":6,\"end\":14}";
        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonDayOff = mapper.writeValueAsString(dayOff);
        String jsonStandardWorkingDay = mapper.writeValueAsString(standardWorkingDay);
        // then
        assertThat(jsonDayOff).isEqualTo(expectedJsonDayOff);
        assertThat(jsonStandardWorkingDay).isEqualTo(expectedJsonStandardWorkingDay);
    }

    @Test
    public void checkJsonToDay() throws Exception {
        // given
        String jsonDayOff = "{\"start\":null,\"end\":null}";
        Day expectedDayOff = new Day();
        String jsonStandardWorkingDay = "{\"start\":6,\"end\":14}";
        Day expectedStandardWorkingDay = new Day(6);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, false);
        // when
        Day dayOff = mapper.readValue(jsonDayOff, Day.class);
        Day standardWorkingDay = mapper.readValue(jsonStandardWorkingDay, Day.class);
        // then
        assertThat(dayOff).isEqualTo(expectedDayOff);
        assertThat(dayOff.getStart()).isNull();
        assertThat(dayOff.getEnd()).isNull();
        assertThat(standardWorkingDay).isEqualTo(expectedStandardWorkingDay);
        assertThat(standardWorkingDay.getStart()).isEqualTo(6);
        assertThat(standardWorkingDay.getEnd()).isEqualTo(14);
    }
}