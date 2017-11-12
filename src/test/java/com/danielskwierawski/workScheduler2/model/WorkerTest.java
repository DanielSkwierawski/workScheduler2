package com.danielskwierawski.workScheduler2.model;

import com.danielskwierawski.workScheduler2.model.Day;
import com.danielskwierawski.workScheduler2.model.Worker;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkerTest {

    Worker sut;

    @Test
    public void checkCreationOfWorker() throws Exception {
        // given
        String name = "Zbigniew";
        String surname = "Kowalski";
        // when
        sut = new Worker(name, surname);
        // then
        assertThat(sut.getName()).isEqualTo(name);
        assertThat(sut.getSurname()).isEqualTo(surname);
    }

    @Test
    public void initializeWorkSchedule() throws Exception {
        // given
        sut = new Worker("Zbigniew", "Kowalski");

        LocalDate firstDay = LocalDate.of(2017, 12, 1);
        LocalDate secondDay = LocalDate.of(2017, 12, 2);
        LocalDate lastDay = LocalDate.of(2017, 12, 3);

        Map<LocalDate, Day> expected = new HashMap<>();
        expected.put(firstDay, new Day());
        expected.put(secondDay, new Day());
        expected.put(lastDay, new Day());
        // when
        sut.initializeWorkSchedule(firstDay, lastDay);
        Map<LocalDate, Day> result = sut.returnWorkSchedule();
        // then
        assertThat(result).size().isEqualTo(3);
        assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void dropWorkSchedule() throws Exception {
        // given
        sut = new Worker("Zbigniew", "Kowalski");

        LocalDate firstDay = LocalDate.of(2017, 12, 1);
        LocalDate secondDay = LocalDate.of(2017, 12, 2);
        LocalDate lastDay = LocalDate.of(2017, 12, 3);

        Map<LocalDate, Day> expected = new HashMap<>();
        expected.put(lastDay, new Day());

        sut.initializeWorkSchedule(firstDay, lastDay);
        // when
        sut.dropWorkSchedule(firstDay, secondDay);
        Map<LocalDate, Day> result = sut.returnWorkSchedule();
        // then
        assertThat(result).size().isEqualTo(1);
        assertThat(result).containsAllEntriesOf(expected);
    }
}