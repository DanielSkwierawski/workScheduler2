package com.danielskwierawski.workScheduler2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanTest {

    Plan sut;

    @Test
    public void checkAddWorker() throws Exception {
        // given
        sut = new Plan();
        Worker worker1 = new Worker("Artur", "Kowalski");
        Worker worker2 = new Worker("Zbigniew", "Wisniewski");
        // when
        sut.addWorker(worker1);
        sut.addWorker(worker2);
        // then
        assertThat(sut.getWorkerList()).containsExactly(worker1, worker2);
    }

    @Test
    public void initializeWorkSchedule() throws Exception {
        // given
        sut = new Plan();
        Worker worker1 = new Worker("Artur", "Kowalski");
        Worker worker2 = new Worker("Zbigniew", "Wisniewski");
        sut.addWorker(worker1);
        sut.addWorker(worker2);

        LocalDate firstDay = LocalDate.of(2017, 12, 1);
        LocalDate secondDay = LocalDate.of(2017, 12, 2);
        LocalDate lastDay = LocalDate.of(2017, 12, 3);
        Day dayOff = new Day();

        Map<LocalDate, Day> expected = new HashMap<>();
        expected.put(firstDay, new Day());
        expected.put(secondDay, new Day());
        expected.put(lastDay, new Day());
        // when
        sut.initializeWorkSchedule(firstDay, lastDay);
        // then
        assertThat(worker1.getDayMap().get(firstDay).isOff()).isTrue();
        assertThat(worker1.getDayMap().get(firstDay)).isEqualTo(dayOff);
        assertThat(worker1.getDayMap().get(secondDay).isOff()).isTrue();
        assertThat(worker1.getDayMap().get(secondDay)).isEqualTo(dayOff);
        assertThat(worker1.getDayMap().get(lastDay).isOff()).isTrue();
        assertThat(worker1.getDayMap().get(lastDay)).isEqualTo(dayOff);
        assertThat(worker1.getDayMap().size()).isEqualTo(3);
        assertThat(worker1.getDayMap()).containsAllEntriesOf(expected);

        assertThat(worker2.getDayMap().get(firstDay).isOff()).isTrue();
        assertThat(worker2.getDayMap().get(firstDay)).isEqualTo(dayOff);
        assertThat(worker2.getDayMap().get(secondDay).isOff()).isTrue();
        assertThat(worker2.getDayMap().get(secondDay)).isEqualTo(dayOff);
        assertThat(worker2.getDayMap().get(lastDay).isOff()).isTrue();
        assertThat(worker2.getDayMap().get(lastDay)).isEqualTo(dayOff);
        assertThat(worker2.getDayMap().size()).isEqualTo(3);
        assertThat(worker2.getDayMap()).containsAllEntriesOf(expected);
    }

    @Test
    public void checkPlanEmptyToJson() throws Exception {
        // given
        Plan planEmpty = new Plan();
        String expectedJsonPlanEmpty = "{\"workerList\":[]}";
        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonPlanEmpty = mapper.writeValueAsString(planEmpty);
        // then
        assertThat(jsonPlanEmpty).isEqualTo(expectedJsonPlanEmpty);
    }

    @Test
    public void checkPlanWith1WorkerToJson() throws Exception {
        // given
        Plan planWith1Worker = new Plan();
        Worker worker1 = new Worker("Mariusz", "Kowalski");
        planWith1Worker.addWorker(worker1);
        String expectedJsonPlanWith1Worker = "{\"workerList\":[{\"name\":\"Mariusz\",\"surname\":\"Kowalski\",\"dayMap\":[]}]}";
        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonPlanWith1Worker = mapper.writeValueAsString(planWith1Worker);
        // then
        assertThat(jsonPlanWith1Worker).isEqualTo(expectedJsonPlanWith1Worker);
    }

    @Test
    public void checkPlanWith2WorkersToJson() throws Exception {
        // given
        Plan planWith2Workers = new Plan();
        Worker worker2 = new Worker("Zdzislaw", "Wisniewski");
        Worker worker3 = new Worker("Przemyslaw", "Mechanik");
        planWith2Workers.addWorker(worker2);
        planWith2Workers.addWorker(worker3);
        String expectedJsonPlanWith2Workers = "{\"workerList\":[{\"name\":\"Zdzislaw\",\"surname\":\"Wisniewski\",\"dayMap\":[]},{\"name\":\"Przemyslaw\",\"surname\":\"Mechanik\",\"dayMap\":[]}]}";
        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonPlanWith2Workers = mapper.writeValueAsString(planWith2Workers);
        // then
        assertThat(jsonPlanWith2Workers).isEqualTo(expectedJsonPlanWith2Workers);
    }

    @Test
    public void checkPlanWith2WorkersAndInitializedToJson() throws Exception {
        // given
        LocalDate firstDay = LocalDate.of(2017, 1, 1);
        LocalDate lastDay = LocalDate.of(2017, 1, 3);

        Plan planWith2WorkersAndInitialized = new Plan();
        Worker worker4 = new Worker("Maciej", "Mierzejewski");
        Worker worker5 = new Worker("Beata", "Szymoniak");
        planWith2WorkersAndInitialized.addWorker(worker4);
        planWith2WorkersAndInitialized.addWorker(worker5);
        planWith2WorkersAndInitialized.initializeWorkSchedule(firstDay, lastDay);
        String expectedJsonPlanWith2WorkersAndInitialized = "{\"workerList\":[{\"name\":\"Maciej\",\"surname\":\"Mierzejewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Beata\",\"surname\":\"Szymoniak\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]}]}";

        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonPlanWith2WorkersAndInitialized = mapper.writeValueAsString(planWith2WorkersAndInitialized);
        // then
        assertThat(jsonPlanWith2WorkersAndInitialized).isEqualTo(expectedJsonPlanWith2WorkersAndInitialized);
    }

    @Test
    public void checkPlanWith2WorkersWithTheirOwnInitializedDayMapToJson() throws Exception {
        // given
        LocalDate firstDay = LocalDate.of(2017, 1, 1);
        LocalDate secondDay = LocalDate.of(2017, 1, 2);
        LocalDate lastDay = LocalDate.of(2017, 1, 3);

        Plan planWith2WorkersWithTheirOwnInitializedDayMap = new Plan();
        Worker worker6 = new Worker("Zbigniew", "Kaszuba");
        worker6.initializeWorkSchedule(firstDay, secondDay);
        Worker worker7 = new Worker("Justyna", "Nowak");
        worker7.initializeWorkSchedule(secondDay, lastDay);
        planWith2WorkersWithTheirOwnInitializedDayMap.addWorker(worker6);
        planWith2WorkersWithTheirOwnInitializedDayMap.addWorker(worker7);
        String expectedJsonPlanWith2WorkersWithTheirOwnInitializedDayMap = "{\"workerList\":[{\"name\":\"Zbigniew\",\"surname\":\"Kaszuba\",\"dayMap\":[[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Justyna\",\"surname\":\"Nowak\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}]]}]}";

        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonPlanWith2WorkersWithTheirOwnInitializedDayMap = mapper.writeValueAsString(planWith2WorkersWithTheirOwnInitializedDayMap);
        // then
        assertThat(jsonPlanWith2WorkersWithTheirOwnInitializedDayMap).isEqualTo(expectedJsonPlanWith2WorkersWithTheirOwnInitializedDayMap);
    }

    @Test
    public void checkPlanWith2WorkersWithTheirOwnWorkingDayMapToJson() throws Exception {
        // given
        LocalDate firstDay = LocalDate.of(2017, 1, 1);
        LocalDate secondDay = LocalDate.of(2017, 1, 2);
        LocalDate lastDay = LocalDate.of(2017, 1, 3);

        Plan planWith2WorkersWithTheirOwnWorkingDayMap = new Plan();
        Worker worker8 = new Worker("Szymon", "Gdynski");
        worker8.initializeWorkSchedule(firstDay, secondDay);
        worker8.getDayMap().get(firstDay).setDay(6);
        worker8.getDayMap().get(secondDay).setDay(14);
        Worker worker9 = new Worker("Zaneta", "Gdanska");
        worker9.initializeWorkSchedule(secondDay, lastDay);
        worker9.getDayMap().get(secondDay).setDay(6);
        worker9.getDayMap().get(lastDay).setDay(14);
        planWith2WorkersWithTheirOwnWorkingDayMap.addWorker(worker8);
        planWith2WorkersWithTheirOwnWorkingDayMap.addWorker(worker9);
        String expectedJsonPlanWith2WorkersWithTheirOwnWorkingDayMap = "{\"workerList\":[{\"name\":\"Szymon\",\"surname\":\"Gdynski\",\"dayMap\":[[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]},{\"name\":\"Zaneta\",\"surname\":\"Gdanska\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":6,\"end\":14}]]}]}";

        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonPlanWith2WorkersWithTheirOwnWorkingDayMap = mapper.writeValueAsString(planWith2WorkersWithTheirOwnWorkingDayMap);
        // then
        assertThat(jsonPlanWith2WorkersWithTheirOwnWorkingDayMap).isEqualTo(expectedJsonPlanWith2WorkersWithTheirOwnWorkingDayMap);
    }

    @Test
    public void checkPlanWith2WorkersAndThenAddWorkingDaysToJson() throws Exception {
        // given
        LocalDate firstDay = LocalDate.of(2017, 1, 1);
        LocalDate secondDay = LocalDate.of(2017, 1, 2);
        LocalDate lastDay = LocalDate.of(2017, 1, 3);

        Plan planWith2WorkersAndThenAddWorkingDays = new Plan();
        Worker worker10 = new Worker("Adam", "Schumacher");
        Worker worker11 = new Worker("Wladyslaw", "Kubica");
        planWith2WorkersAndThenAddWorkingDays.addWorker(worker10);
        planWith2WorkersAndThenAddWorkingDays.addWorker(worker11);
        planWith2WorkersAndThenAddWorkingDays.initializeWorkSchedule(firstDay, lastDay);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(firstDay).setDay(6);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(secondDay).setDay(6);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(lastDay).setDay(14);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(firstDay).setDay(14);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(secondDay).setDay(14);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(lastDay).setDay(6);
        String expectedJsonPlanWith2WorkersAndThenAddWorkingDays = "{\"workerList\":[{\"name\":\"Adam\",\"surname\":\"Schumacher\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":6,\"end\":14}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]},{\"name\":\"Wladyslaw\",\"surname\":\"Kubica\",\"dayMap\":[[\"2017-01-03\",{\"start\":6,\"end\":14}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":14,\"end\":22}]]}]}";

        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonPlanWith2WorkersAndThenAddWorkingDays = mapper.writeValueAsString(planWith2WorkersAndThenAddWorkingDays);
        // then
        assertThat(jsonPlanWith2WorkersAndThenAddWorkingDays).isEqualTo(expectedJsonPlanWith2WorkersAndThenAddWorkingDays);
    }

    @Test
    public void checkJsonToPlanEmpty() throws Exception {
        // given
        String jsonPlanEmpty = "{\"workerList\":[]}";
        Plan expectedPlanEmpty = new Plan();
        ObjectMapper mapper = new ObjectMapper();
        // when
        Plan planEmpty = mapper.readValue(jsonPlanEmpty, Plan.class);
        // then
        assertThat(planEmpty).isEqualTo(expectedPlanEmpty);
        assertThat(planEmpty.getWorkerList()).isNotNull();
        assertThat(planEmpty.getWorkerList()).isEmpty();//not null but empty
    }

    @Test
    public void checkJsonToPlanWith1Worker() throws Exception {
        // given
        String jsonPlanWith1Worker = "{\"workerList\":[{\"name\":\"Mariusz\",\"surname\":\"Kowalski\",\"dayMap\":[]}]}";
        Plan expectedPlanWith1Worker = new Plan();
        Worker worker1 = new Worker("Mariusz", "Kowalski");
        expectedPlanWith1Worker.addWorker(worker1);
        ObjectMapper mapper = new ObjectMapper();
        // when
        Plan planWith1Worker = mapper.readValue(jsonPlanWith1Worker, Plan.class);
        // then
        assertThat(planWith1Worker).isEqualTo(expectedPlanWith1Worker);
        assertThat(planWith1Worker.getWorkerList()).isEqualTo(Arrays.asList(worker1));
        assertThat(planWith1Worker.getWorkerList().get(planWith1Worker.getWorkerList().indexOf(worker1)).getName()).isEqualTo("Mariusz");
        assertThat(planWith1Worker.getWorkerList().get(planWith1Worker.getWorkerList().indexOf(worker1)).getSurname()).isEqualTo("Kowalski");
        assertThat(planWith1Worker.getWorkerList().get(planWith1Worker.getWorkerList().indexOf(worker1)).getDayMap()).isNotNull();
        assertThat(planWith1Worker.getWorkerList().get(planWith1Worker.getWorkerList().indexOf(worker1)).getDayMap()).isEmpty();
    }

    @Test
    public void checkJsonToPlanWith2Workers() throws Exception {
        // given
        String jsonPlanWith2Workers = "{\"workerList\":[{\"name\":\"Zdzislaw\",\"surname\":\"Wisniewski\",\"dayMap\":[]},{\"name\":\"Przemyslaw\",\"surname\":\"Mechanik\",\"dayMap\":[]}]}";
        Plan expectedPlanWith2Workers = new Plan();
        Worker worker2 = new Worker("Zdzislaw", "Wisniewski");
        Worker worker3 = new Worker("Przemyslaw", "Mechanik");
        expectedPlanWith2Workers.addWorker(worker2);
        expectedPlanWith2Workers.addWorker(worker3);
        ObjectMapper mapper = new ObjectMapper();
        // when
        Plan planWith2Workers = mapper.readValue(jsonPlanWith2Workers, Plan.class);
        // then
        assertThat(planWith2Workers).isEqualTo(expectedPlanWith2Workers);
        assertThat(planWith2Workers.getWorkerList()).isEqualTo(Arrays.asList(worker2, worker3));
        assertThat(planWith2Workers.getWorkerList().get(planWith2Workers.getWorkerList().indexOf(worker2)).getName()).isEqualTo("Zdzislaw");
        assertThat(planWith2Workers.getWorkerList().get(planWith2Workers.getWorkerList().indexOf(worker2)).getSurname()).isEqualTo("Wisniewski");
        assertThat(planWith2Workers.getWorkerList().get(planWith2Workers.getWorkerList().indexOf(worker2)).getDayMap()).isNotNull();
        assertThat(planWith2Workers.getWorkerList().get(planWith2Workers.getWorkerList().indexOf(worker2)).getDayMap()).isEmpty();
        assertThat(planWith2Workers.getWorkerList().get(planWith2Workers.getWorkerList().indexOf(worker3)).getName()).isEqualTo("Przemyslaw");
        assertThat(planWith2Workers.getWorkerList().get(planWith2Workers.getWorkerList().indexOf(worker3)).getSurname()).isEqualTo("Mechanik");
        assertThat(planWith2Workers.getWorkerList().get(planWith2Workers.getWorkerList().indexOf(worker3)).getDayMap()).isNotNull();
        assertThat(planWith2Workers.getWorkerList().get(planWith2Workers.getWorkerList().indexOf(worker3)).getDayMap()).isEmpty();
    }

    @Test
    public void checkJsonToPlanWith2WorkersAndInitialized() throws Exception {
        // given
        LocalDate firstDay = LocalDate.of(2017, 1, 1);
        LocalDate secondDay = LocalDate.of(2017, 1, 2);
        LocalDate lastDay = LocalDate.of(2017, 1, 3);
        Day dayOff = new Day();

        String jsonPlanWith2WorkersAndInitialized = "{\"workerList\":[{\"name\":\"Maciej\",\"surname\":\"Mierzejewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Beata\",\"surname\":\"Szymoniak\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]}]}";
        Plan expectedPlanWith2WorkersAndInitialized = new Plan();
        Worker worker4 = new Worker("Maciej", "Mierzejewski");
        Worker worker5 = new Worker("Beata", "Szymoniak");
        expectedPlanWith2WorkersAndInitialized.addWorker(worker4);
        expectedPlanWith2WorkersAndInitialized.addWorker(worker5);
        expectedPlanWith2WorkersAndInitialized.initializeWorkSchedule(firstDay, lastDay);
        ObjectMapper mapper = new ObjectMapper();
        // when
        Plan planWith2WorkersAndInitialized = mapper.readValue(jsonPlanWith2WorkersAndInitialized, Plan.class);
        // then
        assertThat(planWith2WorkersAndInitialized).isEqualTo(expectedPlanWith2WorkersAndInitialized);
        assertThat(planWith2WorkersAndInitialized.getWorkerList()).isEqualTo(Arrays.asList(worker4, worker5));
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getName()).isEqualTo("Maciej");
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getSurname()).isEqualTo("Mierzejewski");
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap().get(firstDay).isOff()).isTrue();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap().get(firstDay)).isEqualTo(dayOff);
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap().get(secondDay).isOff()).isTrue();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap().get(secondDay)).isEqualTo(dayOff);
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap().get(lastDay).isOff()).isTrue();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap().get(lastDay)).isEqualTo(dayOff);
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getName()).isEqualTo("Beata");
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getSurname()).isEqualTo("Szymoniak");
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap().get(firstDay).isOff()).isTrue();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap().get(firstDay)).isEqualTo(dayOff);
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap().get(secondDay).isOff()).isTrue();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap().get(secondDay)).isEqualTo(dayOff);
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap().get(lastDay).isOff()).isTrue();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap().get(lastDay)).isEqualTo(dayOff);
    }

    @Test
    public void checkJsonToPlanWith2WorkersWithTheirOwnInitializedDayMap() throws Exception {
        // given
        LocalDate firstDay = LocalDate.of(2017, 1, 1);
        LocalDate secondDay = LocalDate.of(2017, 1, 2);
        LocalDate lastDay = LocalDate.of(2017, 1, 3);
        Day dayOff = new Day();

        String jsonPlanWith2WorkersWithTheirOwnInitializedDayMap = "{\"workerList\":[{\"name\":\"Zbigniew\",\"surname\":\"Kaszuba\",\"dayMap\":[[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Justyna\",\"surname\":\"Nowak\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}]]}]}";
        Plan expectedPlanWith2WorkersWithTheirOwnInitializedDayMap = new Plan();
        Worker worker6 = new Worker("Zbigniew", "Kaszuba");
        worker6.initializeWorkSchedule(firstDay, secondDay);
        Worker worker7 = new Worker("Justyna", "Nowak");
        worker7.initializeWorkSchedule(secondDay, lastDay);
        expectedPlanWith2WorkersWithTheirOwnInitializedDayMap.addWorker(worker6);
        expectedPlanWith2WorkersWithTheirOwnInitializedDayMap.addWorker(worker7);
        ObjectMapper mapper = new ObjectMapper();
        // when
        Plan planWith2WorkersWithTheirOwnInitializedDayMap = mapper.readValue(jsonPlanWith2WorkersWithTheirOwnInitializedDayMap, Plan.class);
        // then
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap).isEqualTo(expectedPlanWith2WorkersWithTheirOwnInitializedDayMap);
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList()).isEqualTo(Arrays.asList(worker6, worker7));
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getName()).isEqualTo("Zbigniew");
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getSurname()).isEqualTo("Kaszuba");
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getDayMap().get(firstDay).isOff()).isTrue();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getDayMap().get(firstDay)).isEqualTo(dayOff);
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getDayMap().get(secondDay).isOff()).isTrue();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getDayMap().get(secondDay)).isEqualTo(dayOff);
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getName()).isEqualTo("Justyna");
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getSurname()).isEqualTo("Nowak");
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getDayMap().get(secondDay).isOff()).isTrue();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getDayMap().get(secondDay)).isEqualTo(dayOff);
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getDayMap().get(lastDay).isOff()).isTrue();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getDayMap().get(lastDay)).isEqualTo(dayOff);
    }

    @Test
    public void checkJsonToPlanWith2WorkersWithTheirOwnWorkingDayMap() throws Exception {
        // given
        LocalDate firstDay = LocalDate.of(2017, 1, 1);
        LocalDate secondDay = LocalDate.of(2017, 1, 2);
        LocalDate lastDay = LocalDate.of(2017, 1, 3);
        Day dayMorning = new Day(6);
        Day dayAfternoon = new Day(14);

        String jsonPlanWith2WorkersWithTheirOwnWorkingDayMap = "{\"workerList\":[{\"name\":\"Szymon\",\"surname\":\"Gdynski\",\"dayMap\":[[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]},{\"name\":\"Zaneta\",\"surname\":\"Gdanska\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":6,\"end\":14}]]}]}";
        Plan expectedPlanWith2WorkersWithTheirOwnWorkingDayMap = new Plan();
        Worker worker8 = new Worker("Szymon", "Gdynski");
        worker8.initializeWorkSchedule(firstDay, secondDay);
        worker8.getDayMap().get(firstDay).setDay(6);
        worker8.getDayMap().get(secondDay).setDay(14);
        Worker worker9 = new Worker("Zaneta", "Gdanska");
        worker9.initializeWorkSchedule(secondDay, lastDay);
        worker9.getDayMap().get(secondDay).setDay(6);
        worker9.getDayMap().get(lastDay).setDay(14);
        expectedPlanWith2WorkersWithTheirOwnWorkingDayMap.addWorker(worker8);
        expectedPlanWith2WorkersWithTheirOwnWorkingDayMap.addWorker(worker9);

        ObjectMapper mapper = new ObjectMapper();
        // when
        Plan planWith2WorkersWithTheirOwnWorkingDayMap = mapper.readValue(jsonPlanWith2WorkersWithTheirOwnWorkingDayMap, Plan.class);
        // then
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap).isEqualTo(expectedPlanWith2WorkersWithTheirOwnWorkingDayMap);
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList()).isEqualTo(Arrays.asList(worker8, worker9));
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getName()).isEqualTo("Szymon");
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getSurname()).isEqualTo("Gdynski");
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getDayMap().get(firstDay).isOff()).isFalse();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getDayMap().get(firstDay)).isEqualTo(dayMorning);
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getDayMap().get(secondDay).isOff()).isFalse();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getDayMap().get(secondDay)).isEqualTo(dayAfternoon);
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getName()).isEqualTo("Zaneta");
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getSurname()).isEqualTo("Gdanska");
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getDayMap().get(secondDay).isOff()).isFalse();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getDayMap().get(secondDay)).isEqualTo(dayMorning);
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getDayMap().get(lastDay).isOff()).isFalse();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getDayMap().get(lastDay)).isEqualTo(dayAfternoon);
    }

    @Test
    public void checkJsonToPlanWith2WorkersAndThenAddWorkingDays() throws Exception {
        // given
        LocalDate firstDay = LocalDate.of(2017, 1, 1);
        LocalDate secondDay = LocalDate.of(2017, 1, 2);
        LocalDate lastDay = LocalDate.of(2017, 1, 3);
        Day dayMorning = new Day(6);
        Day dayAfternoon = new Day(14);

        String jsonPlanWith2WorkersAndThenAddWorkingDays = "{\"workerList\":[{\"name\":\"Adam\",\"surname\":\"Schumacher\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":6,\"end\":14}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]},{\"name\":\"Wladyslaw\",\"surname\":\"Kubica\",\"dayMap\":[[\"2017-01-03\",{\"start\":6,\"end\":14}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":14,\"end\":22}]]}]}";
        Plan expectedPlanWith2WorkersAndThenAddWorkingDays = new Plan();
        Worker worker10 = new Worker("Adam", "Schumacher");
        Worker worker11 = new Worker("Wladyslaw", "Kubica");
        expectedPlanWith2WorkersAndThenAddWorkingDays.addWorker(worker10);
        expectedPlanWith2WorkersAndThenAddWorkingDays.addWorker(worker11);
        expectedPlanWith2WorkersAndThenAddWorkingDays.initializeWorkSchedule(firstDay, lastDay);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(firstDay).setDay(6);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(secondDay).setDay(6);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(lastDay).setDay(14);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(firstDay).setDay(14);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(secondDay).setDay(14);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(lastDay).setDay(6);

        ObjectMapper mapper = new ObjectMapper();
        // when
        Plan planWith2WorkersAndThenAddWorkingDays = mapper.readValue(jsonPlanWith2WorkersAndThenAddWorkingDays, Plan.class);
        // then
        assertThat(planWith2WorkersAndThenAddWorkingDays).isEqualTo(expectedPlanWith2WorkersAndThenAddWorkingDays);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList()).isEqualTo(Arrays.asList(worker10, worker11));
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getName()).isEqualTo("Adam");
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getSurname()).isEqualTo("Schumacher");
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(firstDay).isOff()).isFalse();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(firstDay)).isEqualTo(dayMorning);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(secondDay).isOff()).isFalse();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(secondDay)).isEqualTo(dayMorning);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(lastDay).isOff()).isFalse();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(lastDay)).isEqualTo(dayAfternoon);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getName()).isEqualTo("Wladyslaw");
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getSurname()).isEqualTo("Kubica");
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(firstDay).isOff()).isFalse();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(firstDay)).isEqualTo(dayAfternoon);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(secondDay).isOff()).isFalse();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(secondDay)).isEqualTo(dayAfternoon);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(lastDay).isOff()).isFalse();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(lastDay)).isEqualTo(dayMorning);
    }
}