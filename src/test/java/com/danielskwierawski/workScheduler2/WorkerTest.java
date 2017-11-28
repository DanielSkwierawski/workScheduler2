package com.danielskwierawski.workScheduler2;

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
        Map<LocalDate, Day> result = sut.getDayMap();
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
        Map<LocalDate, Day> result = sut.getDayMap();
        // then
        assertThat(result).size().isEqualTo(1);
        assertThat(result).containsAllEntriesOf(expected);
    }

    @Test
    public void checkWorkerEmptyToJson() throws Exception {
        // given
        Worker workerEmpty = new Worker("Artur", "Kowalski");
        String expectedJsonWorkerEmpty = "{\"name\":\"Artur\",\"surname\":\"Kowalski\",\"dayMap\":[]}";
        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonWorkerEmpty = mapper.writeValueAsString(workerEmpty);
        // then
        assertThat(jsonWorkerEmpty).isEqualTo(expectedJsonWorkerEmpty);
    }

    @Test
    public void checkWorkerInitializedToJson() throws Exception {
        // given
        LocalDate start = LocalDate.of(2017, 1, 1);
        LocalDate end = LocalDate.of(2017, 1, 3);

        Worker workerInitialized = new Worker("Maciej", "Wisniewski");
        workerInitialized.initializeWorkSchedule(start, end);

        String expectedJsonWorkerInitialized = "{\"name\":\"Maciej\",\"surname\":\"Wisniewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]}";

        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonWorkerInitialized = mapper.writeValueAsString(workerInitialized);
        // then
        assertThat(jsonWorkerInitialized).isEqualTo(expectedJsonWorkerInitialized);
    }

    @Test
    public void checkWorkerWorkingToJson() throws Exception {
        // given
        LocalDate start = LocalDate.of(2017, 1, 1);
        LocalDate middle = LocalDate.of(2017, 1, 2);
        LocalDate end = LocalDate.of(2017, 1, 3);

        Worker workerWorking = new Worker("Michal", "Pracujacy");
        workerWorking.initializeWorkSchedule(start, end);
        workerWorking.getDayMap().get(start).setDay(6);
        workerWorking.getDayMap().get(middle).setDay(14);
        workerWorking.getDayMap().get(end).setDay(14);

        String expectedJsonWorkerWorking = "{\"name\":\"Michal\",\"surname\":\"Pracujacy\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]}";

        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonWorkerWorking = mapper.writeValueAsString(workerWorking);
        // then
        assertThat(jsonWorkerWorking).isEqualTo(expectedJsonWorkerWorking);

    }

    @Test
    public void checkJsonToWorkerEmpty() throws Exception {
        // given
        String jsonWorkerEmpty = "{\"name\":\"Artur\",\"surname\":\"Kowalski\",\"dayMap\":[]}";
        Worker expectedWorkerEmpty = new Worker("Artur", "Kowalski");
        ObjectMapper mapper = new ObjectMapper();
        // when
        Worker workerEmpty = mapper.readValue(jsonWorkerEmpty, Worker.class);
        // then
        assertThat(workerEmpty).isEqualTo(expectedWorkerEmpty);
        assertThat(workerEmpty.getName()).isEqualTo("Artur");
        assertThat(workerEmpty.getSurname()).isEqualTo("Kowalski");
        assertThat(workerEmpty.getDayMap()).isNotNull();
        assertThat(workerEmpty.getDayMap()).isEmpty();//not null but empty
    }

    @Test
    public void checkJsonToWorkerInitialized() throws Exception {
        // given
        LocalDate start = LocalDate.of(2017, 1, 1);
        LocalDate middle = LocalDate.of(2017, 1, 2);
        LocalDate end = LocalDate.of(2017, 1, 3);
        Day dayOff = new Day();

        String jsonWorkerInitialized = "{\"name\":\"Maciej\",\"surname\":\"Wisniewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]}";

        Worker expectedWorkerInitialized = new Worker("Maciej", "Wisniewski");
        expectedWorkerInitialized.initializeWorkSchedule(start, end);

        ObjectMapper mapper = new ObjectMapper();
        // when
        Worker workerInitialized = mapper.readValue(jsonWorkerInitialized, Worker.class);
        // then
        assertThat(workerInitialized).isEqualTo(expectedWorkerInitialized);
        assertThat(workerInitialized.getName()).isEqualTo("Maciej");
        assertThat(workerInitialized.getSurname()).isEqualTo("Wisniewski");
        assertThat(workerInitialized.getDayMap()).isNotNull();
        assertThat(workerInitialized.getDayMap()).isNotEmpty();
        assertThat(workerInitialized.getDayMap().get(start)).isEqualTo(dayOff);
        assertThat(workerInitialized.getDayMap().get(start).isOff()).isTrue();
        assertThat(workerInitialized.getDayMap().get(middle)).isEqualTo(dayOff);
        assertThat(workerInitialized.getDayMap().get(middle).isOff()).isTrue();
        assertThat(workerInitialized.getDayMap().get(end)).isEqualTo(dayOff);
        assertThat(workerInitialized.getDayMap().get(end).isOff()).isTrue();
    }

    @Test
    public void checkJsonToWorkerWorking() throws Exception {
        // given
        LocalDate start = LocalDate.of(2017, 1, 1);
        LocalDate middle = LocalDate.of(2017, 1, 2);
        LocalDate end = LocalDate.of(2017, 1, 3);

        String jsonWorkerWorking = "{\"name\":\"Michal\",\"surname\":\"Pracujacy\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]}";
        Worker expectedWorkerWorking = new Worker("Michal", "Pracujacy");
        expectedWorkerWorking.initializeWorkSchedule(start, end);
        expectedWorkerWorking.getDayMap().get(start).setDay(6);
        expectedWorkerWorking.getDayMap().get(middle).setDay(14);
        expectedWorkerWorking.getDayMap().get(end).setDay(14);

        ObjectMapper mapper = new ObjectMapper();
        // when
        Worker workerWorking = mapper.readValue(jsonWorkerWorking, Worker.class);
        // then
        assertThat(workerWorking).isEqualTo(expectedWorkerWorking);
        assertThat(workerWorking.getName()).isEqualTo("Michal");
        assertThat(workerWorking.getSurname()).isEqualTo("Pracujacy");
        assertThat(workerWorking.getDayMap()).isNotNull();
        assertThat(workerWorking.getDayMap()).isNotEmpty();
        assertThat(workerWorking.getDayMap().get(start).isOff()).isFalse();
        assertThat(workerWorking.getDayMap().get(start).getStart()).isEqualTo(6);
        assertThat(workerWorking.getDayMap().get(start).getEnd()).isEqualTo(14);
        assertThat(workerWorking.getDayMap().get(middle).isOff()).isFalse();
        assertThat(workerWorking.getDayMap().get(middle).getStart()).isEqualTo(14);
        assertThat(workerWorking.getDayMap().get(middle).getEnd()).isEqualTo(22);
        assertThat(workerWorking.getDayMap().get(end).isOff()).isFalse();
        assertThat(workerWorking.getDayMap().get(end).getStart()).isEqualTo(14);
        assertThat(workerWorking.getDayMap().get(end).getEnd()).isEqualTo(22);
    }
}