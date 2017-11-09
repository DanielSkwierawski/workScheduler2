package com.danielskwierawski.workScheduler2;

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
        LocalDate secondDay = LocalDate.of(2017, 12, 1);
        LocalDate lastDay = LocalDate.of(2017, 12, 3);

        Map<LocalDate, Day> expected = new HashMap<>();
        expected.put(firstDay, new Day(firstDay));
        expected.put(secondDay, new Day(secondDay));
        expected.put(lastDay, new Day(lastDay));
        // when
        sut.initializeWorkSchedule(firstDay, lastDay);
        Map<LocalDate, Day> result = sut.returnWorkSchedule();
        // then
        assertThat(result).size().isEqualTo(3);
        assertThat(result).containsAllEntriesOf(expected);
    }
}