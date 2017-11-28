package com.danielskwierawski.workScheduler2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanTest {

    Plan sut;

    @Test
    public void name() throws Exception {
        // given
        sut = new Plan();
        Worker worker1 = new Worker("Artur", "Kowalski");
        Worker worker2 = new Worker("Zbigniew", "Wisniewski");
        List<Worker> workerList = new ArrayList<>();
        workerList.add(worker1);
        workerList.add(worker2);
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

        Map<LocalDate, Day> expected = new HashMap<>();
        expected.put(firstDay, new Day());
        expected.put(secondDay, new Day());
        expected.put(lastDay, new Day());
        // when
        sut.initializeWorkSchedule(firstDay, lastDay);
        // then
        List<Worker> allWorkers = sut.getWorkerList();
        for (Worker worker : allWorkers) {
            Map<LocalDate, Day> result = worker.returnWorkSchedule();
            assertThat(result).size().isEqualTo(3);
            assertThat(result).containsAllEntriesOf(expected);
        }

    }

    @Test
    public void checkPlanToJson() throws Exception {
        // given
        LocalDate start = LocalDate.of(2017, 1, 1);
        LocalDate middle = LocalDate.of(2017, 1, 2);
        LocalDate end = LocalDate.of(2017, 1, 3);

        Plan planEmpty = new Plan();
        String expectedJsonPlanEmpty = "{\"workerList\":[]}";

        Plan planWith1Worker = new Plan();
        Worker worker1 = new Worker("Mariusz", "Kowalski");
        planWith1Worker.addWorker(worker1);
        String expectedJsonPlanWith1Worker = "{\"workerList\":[{\"name\":\"Mariusz\",\"surname\":\"Kowalski\",\"dayMap\":[]}]}";

        Plan planWith2Workers = new Plan();
        Worker worker2 = new Worker("Zdzislaw", "Wisniewski");
        Worker worker3 = new Worker("Przemyslaw", "Mechanik");
        planWith2Workers.addWorker(worker2);
        planWith2Workers.addWorker(worker3);
        String expectedJsonPlanWith2Workers = "{\"workerList\":[{\"name\":\"Zdzislaw\",\"surname\":\"Wisniewski\",\"dayMap\":[]},{\"name\":\"Przemyslaw\",\"surname\":\"Mechanik\",\"dayMap\":[]}]}";

        Plan planWith2WorkersAndInitialized = new Plan();
        Worker worker4 = new Worker("Maciej", "Mierzejewski");
        Worker worker5 = new Worker("Beata", "Szymoniak");
        planWith2WorkersAndInitialized.addWorker(worker4);
        planWith2WorkersAndInitialized.addWorker(worker5);
        planWith2WorkersAndInitialized.initializeWorkSchedule(start, end);
        String expectedJsonPlanWith2WorkersAndInitialized = "{\"workerList\":[{\"name\":\"Maciej\",\"surname\":\"Mierzejewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Beata\",\"surname\":\"Szymoniak\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]}]}";

        Plan planWith2WorkersWithTheirOwnInitializedDayMap = new Plan();
        Worker worker6 = new Worker("Zbigniew", "Kaszuba");
        worker6.initializeWorkSchedule(start, middle);
        Worker worker7 = new Worker("Justyna", "Nowak");
        worker7.initializeWorkSchedule(middle, end);
        planWith2WorkersWithTheirOwnInitializedDayMap.addWorker(worker6);
        planWith2WorkersWithTheirOwnInitializedDayMap.addWorker(worker7);
        String expectedJsonPlanWith2WorkersWithTheirOwnInitializedDayMap = "{\"workerList\":[{\"name\":\"Zbigniew\",\"surname\":\"Kaszuba\",\"dayMap\":[[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Justyna\",\"surname\":\"Nowak\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}]]}]}";

        Plan planWith2WorkersWithTheirOwnWorkingDayMap = new Plan();
        Worker worker8 = new Worker("Szymon", "Gdynski");
        worker8.initializeWorkSchedule(start, middle);
        worker8.getDayMap().get(start).setDay(6);
        worker8.getDayMap().get(middle).setDay(14);
        Worker worker9 = new Worker("Zaneta", "Gdanska");
        worker9.initializeWorkSchedule(middle, end);
        worker9.getDayMap().get(middle).setDay(6);
        worker9.getDayMap().get(end).setDay(14);
        planWith2WorkersWithTheirOwnWorkingDayMap.addWorker(worker8);
        planWith2WorkersWithTheirOwnWorkingDayMap.addWorker(worker9);
        String expectedJsonPlanWith2WorkersWithTheirOwnWorkingDayMap = "{\"workerList\":[{\"name\":\"Szymon\",\"surname\":\"Gdynski\",\"dayMap\":[[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]},{\"name\":\"Zaneta\",\"surname\":\"Gdanska\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":6,\"end\":14}]]}]}";

        Plan planWith2WorkersAndThenAddWorkingDays = new Plan();
        Worker worker10 = new Worker("Adam", "Schumacher");
        Worker worker11 = new Worker("Wladyslaw", "Kubica");
        planWith2WorkersAndThenAddWorkingDays.addWorker(worker10);
        planWith2WorkersAndThenAddWorkingDays.addWorker(worker11);
        planWith2WorkersAndThenAddWorkingDays.initializeWorkSchedule(start, end);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(start).setDay(6);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(middle).setDay(6);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(end).setDay(14);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(start).setDay(14);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(middle).setDay(14);
        planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(end).setDay(6);
        String expectedJsonPlanWith2WorkersAndThenAddWorkingDays = "{\"workerList\":[{\"name\":\"Adam\",\"surname\":\"Schumacher\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":6,\"end\":14}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]},{\"name\":\"Wladyslaw\",\"surname\":\"Kubica\",\"dayMap\":[[\"2017-01-03\",{\"start\":6,\"end\":14}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":14,\"end\":22}]]}]}";
        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonPlanEmpty = mapper.writeValueAsString(planEmpty);
        String jsonPlanWith1Worker = mapper.writeValueAsString(planWith1Worker);
        String jsonPlanWith2Workers = mapper.writeValueAsString(planWith2Workers);
        String jsonPlanWith2WorkersAndInitialized = mapper.writeValueAsString(planWith2WorkersAndInitialized);
        String jsonPlanWith2WorkersWithTheirOwnInitializedDayMap = mapper.writeValueAsString(planWith2WorkersWithTheirOwnInitializedDayMap);
        String jsonPlanWith2WorkersWithTheirOwnWorkingDayMap = mapper.writeValueAsString(planWith2WorkersWithTheirOwnWorkingDayMap);
        String jsonPlanWith2WorkersAndThenAddWorkingDays = mapper.writeValueAsString(planWith2WorkersAndThenAddWorkingDays);
        // then
        assertThat(jsonPlanEmpty).isEqualTo(expectedJsonPlanEmpty);
        assertThat(jsonPlanWith1Worker).isEqualTo(expectedJsonPlanWith1Worker);
        assertThat(jsonPlanWith2Workers).isEqualTo(expectedJsonPlanWith2Workers);
        assertThat(jsonPlanWith2WorkersAndInitialized).isEqualTo(expectedJsonPlanWith2WorkersAndInitialized);
        assertThat(jsonPlanWith2WorkersWithTheirOwnInitializedDayMap).isEqualTo(expectedJsonPlanWith2WorkersWithTheirOwnInitializedDayMap);
        assertThat(jsonPlanWith2WorkersWithTheirOwnWorkingDayMap).isEqualTo(expectedJsonPlanWith2WorkersWithTheirOwnWorkingDayMap);
        assertThat(jsonPlanWith2WorkersAndThenAddWorkingDays).isEqualTo(expectedJsonPlanWith2WorkersAndThenAddWorkingDays);
    }

    @Test
    public void checkJsonToPlan() throws Exception {
        // given
        LocalDate start = LocalDate.of(2017, 1, 1);
        LocalDate middle = LocalDate.of(2017, 1, 2);
        LocalDate end = LocalDate.of(2017, 1, 3);
        Day dayOff = new Day();
        Day dayMorning = new Day(6);
        Day dayAfternoon = new Day(14);

        String jsonPlanEmpty = "{\"workerList\":[]}";
        Plan expectedPlanEmpty = new Plan();

        String jsonPlanWith1Worker = "{\"workerList\":[{\"name\":\"Mariusz\",\"surname\":\"Kowalski\",\"dayMap\":[]}]}";
        Plan expectedPlanWith1Worker = new Plan();
        Worker worker1 = new Worker("Mariusz", "Kowalski");
        expectedPlanWith1Worker.addWorker(worker1);

        String jsonPlanWith2Workers = "{\"workerList\":[{\"name\":\"Zdzislaw\",\"surname\":\"Wisniewski\",\"dayMap\":[]},{\"name\":\"Przemyslaw\",\"surname\":\"Mechanik\",\"dayMap\":[]}]}";
        Plan expectedPlanWith2Workers = new Plan();
        Worker worker2 = new Worker("Zdzislaw", "Wisniewski");
        Worker worker3 = new Worker("Przemyslaw", "Mechanik");
        expectedPlanWith2Workers.addWorker(worker2);
        expectedPlanWith2Workers.addWorker(worker3);

        String jsonPlanWith2WorkersAndInitialized = "{\"workerList\":[{\"name\":\"Maciej\",\"surname\":\"Mierzejewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Beata\",\"surname\":\"Szymoniak\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]}]}";
        Plan expectedPlanWith2WorkersAndInitialized = new Plan();
        Worker worker4 = new Worker("Maciej", "Mierzejewski");
        Worker worker5 = new Worker("Beata", "Szymoniak");
        expectedPlanWith2WorkersAndInitialized.addWorker(worker4);
        expectedPlanWith2WorkersAndInitialized.addWorker(worker5);
        expectedPlanWith2WorkersAndInitialized.initializeWorkSchedule(start, end);

        String jsonPlanWith2WorkersWithTheirOwnInitializedDayMap = "{\"workerList\":[{\"name\":\"Zbigniew\",\"surname\":\"Kaszuba\",\"dayMap\":[[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Justyna\",\"surname\":\"Nowak\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}]]}]}";
        Plan expectedPlanWith2WorkersWithTheirOwnInitializedDayMap = new Plan();
        Worker worker6 = new Worker("Zbigniew", "Kaszuba");
        worker6.initializeWorkSchedule(start, middle);
        Worker worker7 = new Worker("Justyna", "Nowak");
        worker7.initializeWorkSchedule(middle, end);
        expectedPlanWith2WorkersWithTheirOwnInitializedDayMap.addWorker(worker6);
        expectedPlanWith2WorkersWithTheirOwnInitializedDayMap.addWorker(worker7);

        String jsonPlanWith2WorkersWithTheirOwnWorkingDayMap = "{\"workerList\":[{\"name\":\"Szymon\",\"surname\":\"Gdynski\",\"dayMap\":[[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]},{\"name\":\"Zaneta\",\"surname\":\"Gdanska\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":6,\"end\":14}]]}]}";
        Plan expectedPlanWith2WorkersWithTheirOwnWorkingDayMap = new Plan();
        Worker worker8 = new Worker("Szymon", "Gdynski");
        worker8.initializeWorkSchedule(start, middle);
        worker8.getDayMap().get(start).setDay(6);
        worker8.getDayMap().get(middle).setDay(14);
        Worker worker9 = new Worker("Zaneta", "Gdanska");
        worker9.initializeWorkSchedule(middle, end);
        worker9.getDayMap().get(middle).setDay(6);
        worker9.getDayMap().get(end).setDay(14);
        expectedPlanWith2WorkersWithTheirOwnWorkingDayMap.addWorker(worker8);
        expectedPlanWith2WorkersWithTheirOwnWorkingDayMap.addWorker(worker9);

        String jsonPlanWith2WorkersAndThenAddWorkingDays = "{\"workerList\":[{\"name\":\"Adam\",\"surname\":\"Schumacher\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":6,\"end\":14}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]},{\"name\":\"Wladyslaw\",\"surname\":\"Kubica\",\"dayMap\":[[\"2017-01-03\",{\"start\":6,\"end\":14}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":14,\"end\":22}]]}]}";
        Plan expectedPlanWith2WorkersAndThenAddWorkingDays = new Plan();
        Worker worker10 = new Worker("Adam", "Schumacher");
        Worker worker11 = new Worker("Wladyslaw", "Kubica");
        expectedPlanWith2WorkersAndThenAddWorkingDays.addWorker(worker10);
        expectedPlanWith2WorkersAndThenAddWorkingDays.addWorker(worker11);
        expectedPlanWith2WorkersAndThenAddWorkingDays.initializeWorkSchedule(start, end);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(start).setDay(6);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(middle).setDay(6);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(end).setDay(14);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(start).setDay(14);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(middle).setDay(14);
        expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().get(expectedPlanWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(end).setDay(6);

        ObjectMapper mapper = new ObjectMapper();
        // when
        Plan planEmpty = mapper.readValue(jsonPlanEmpty, Plan.class);
        Plan planWith1Worker = mapper.readValue(jsonPlanWith1Worker, Plan.class);
        Plan planWith2Workers = mapper.readValue(jsonPlanWith2Workers, Plan.class);
        Plan planWith2WorkersAndInitialized = mapper.readValue(jsonPlanWith2WorkersAndInitialized, Plan.class);
        Plan planWith2WorkersWithTheirOwnInitializedDayMap = mapper.readValue(jsonPlanWith2WorkersWithTheirOwnInitializedDayMap, Plan.class);
        Plan planWith2WorkersWithTheirOwnWorkingDayMap = mapper.readValue(jsonPlanWith2WorkersWithTheirOwnWorkingDayMap, Plan.class);
        Plan planWith2WorkersAndThenAddWorkingDays = mapper.readValue(jsonPlanWith2WorkersAndThenAddWorkingDays, Plan.class);
        // then

        assertThat(planEmpty).isEqualTo(expectedPlanEmpty);
        assertThat(planEmpty.getWorkerList()).isNotNull();
        assertThat(planEmpty.getWorkerList()).isEmpty();//not null but empty

        assertThat(planWith1Worker).isEqualTo(expectedPlanWith1Worker);
        assertThat(planWith1Worker.getWorkerList()).isEqualTo(Arrays.asList(worker1));
        assertThat(planWith1Worker.getWorkerList().get(planWith1Worker.getWorkerList().indexOf(worker1)).getName()).isEqualTo("Mariusz");
        assertThat(planWith1Worker.getWorkerList().get(planWith1Worker.getWorkerList().indexOf(worker1)).getSurname()).isEqualTo("Kowalski");
        assertThat(planWith1Worker.getWorkerList().get(planWith1Worker.getWorkerList().indexOf(worker1)).getDayMap()).isNotNull();
        assertThat(planWith1Worker.getWorkerList().get(planWith1Worker.getWorkerList().indexOf(worker1)).getDayMap()).isEmpty();

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

        assertThat(planWith2WorkersAndInitialized).isEqualTo(expectedPlanWith2WorkersAndInitialized);
        assertThat(planWith2WorkersAndInitialized.getWorkerList()).isEqualTo(Arrays.asList(worker4, worker5));
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getName()).isEqualTo("Maciej");
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getSurname()).isEqualTo("Mierzejewski");
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap().get(start)).isEqualTo(dayOff);
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap().get(middle)).isEqualTo(dayOff);
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker4)).getDayMap().get(end)).isEqualTo(dayOff);
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getName()).isEqualTo("Beata");
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getSurname()).isEqualTo("Szymoniak");
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap().get(start)).isEqualTo(dayOff);
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap().get(middle)).isEqualTo(dayOff);
        assertThat(planWith2WorkersAndInitialized.getWorkerList().get(planWith2WorkersAndInitialized.getWorkerList().indexOf(worker5)).getDayMap().get(end)).isEqualTo(dayOff);

        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap).isEqualTo(expectedPlanWith2WorkersWithTheirOwnInitializedDayMap);
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList()).isEqualTo(Arrays.asList(worker6, worker7));
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getName()).isEqualTo("Zbigniew");
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getSurname()).isEqualTo("Kaszuba");
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getDayMap().get(start)).isEqualTo(dayOff);
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker6)).getDayMap().get(middle)).isEqualTo(dayOff);
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getName()).isEqualTo("Justyna");
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getSurname()).isEqualTo("Nowak");
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getDayMap().get(middle)).isEqualTo(dayOff);
        assertThat(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnInitializedDayMap.getWorkerList().indexOf(worker7)).getDayMap().get(end)).isEqualTo(dayOff);

        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap).isEqualTo(expectedPlanWith2WorkersWithTheirOwnWorkingDayMap);
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList()).isEqualTo(Arrays.asList(worker8, worker9));
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getName()).isEqualTo("Szymon");
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getSurname()).isEqualTo("Gdynski");
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getDayMap().get(start)).isEqualTo(dayMorning);
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker8)).getDayMap().get(middle)).isEqualTo(dayAfternoon);
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getName()).isEqualTo("Zaneta");
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getSurname()).isEqualTo("Gdanska");
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getDayMap().get(middle)).isEqualTo(dayMorning);
        assertThat(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().get(planWith2WorkersWithTheirOwnWorkingDayMap.getWorkerList().indexOf(worker9)).getDayMap().get(end)).isEqualTo(dayAfternoon);

        assertThat(planWith2WorkersAndThenAddWorkingDays).isEqualTo(expectedPlanWith2WorkersAndThenAddWorkingDays);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList()).isEqualTo(Arrays.asList(worker10, worker11));
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getName()).isEqualTo("Adam");
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getSurname()).isEqualTo("Schumacher");
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(start)).isEqualTo(dayMorning);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(middle)).isEqualTo(dayMorning);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker10)).getDayMap().get(end)).isEqualTo(dayAfternoon);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getName()).isEqualTo("Wladyslaw");
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getSurname()).isEqualTo("Kubica");
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap()).isNotNull();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap()).isNotEmpty();
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(start)).isEqualTo(dayAfternoon);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(middle)).isEqualTo(dayAfternoon);
        assertThat(planWith2WorkersAndThenAddWorkingDays.getWorkerList().get(planWith2WorkersAndThenAddWorkingDays.getWorkerList().indexOf(worker11)).getDayMap().get(end)).isEqualTo(dayMorning);
    }
}