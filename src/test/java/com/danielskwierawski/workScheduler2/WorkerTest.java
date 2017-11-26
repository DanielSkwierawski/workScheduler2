package com.danielskwierawski.workScheduler2;

import com.danielskwierawski.workScheduler2.Day;
import com.danielskwierawski.workScheduler2.Worker;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Test
    public void checkWorkerToJsonByJackson() throws Exception {
        // given
        LocalDate start = LocalDate.of(2017,1,1);
        LocalDate middle = LocalDate.of(2017,1,2);
        LocalDate end = LocalDate.of(2017,1,3);

        Worker workerEmpty = new Worker("Artur", "Kowalski");
        String expectedJsonWorkerEmpty = "{\"name\":\"Artur\",\"surname\":\"Kowalski\",\"dayMap\":[]}";

        Worker workerInitialized = new Worker("Maciej", "Wisniewski");
        workerInitialized.initializeWorkSchedule(start, end);
        String expectedJsonWorkerInitialized = "{\"name\":\"Maciej\",\"surname\":\"Wisniewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]}";

        Worker workerWorking = new Worker("Michal", "Pracujacy");
        workerWorking.initializeWorkSchedule(start, end);
        workerWorking.getDayMap().get(start).setStart(6);
        workerWorking.getDayMap().get(start).setEnd(14);
        workerWorking.getDayMap().get(middle).setStart(14);
        workerWorking.getDayMap().get(middle).setEnd(22);
        workerWorking.getDayMap().get(end).setStart(14);
        workerWorking.getDayMap().get(end).setEnd(22);
        String expectedJsonWorkerWorking = "{\"name\":\"Michal\",\"surname\":\"Pracujacy\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]}";

        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonWorkerEmpty = mapper.writeValueAsString(workerEmpty);
        String jsonWorkerInitialized = mapper.writeValueAsString(workerInitialized);
        String jsonWorkerWorking = mapper.writeValueAsString(workerWorking);
        // then
        assertThat(jsonWorkerEmpty).isEqualTo(expectedJsonWorkerEmpty);
        assertThat(jsonWorkerInitialized).isEqualTo(expectedJsonWorkerInitialized);
        assertThat(jsonWorkerWorking).isEqualTo(expectedJsonWorkerWorking);

    }
}