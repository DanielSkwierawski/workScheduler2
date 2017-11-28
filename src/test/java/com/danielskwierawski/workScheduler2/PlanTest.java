package com.danielskwierawski.workScheduler2;

import com.danielskwierawski.workScheduler2.Day;
import com.danielskwierawski.workScheduler2.Plan;
import com.danielskwierawski.workScheduler2.Worker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

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
        assertThat(sut.getAllWorkers()).containsExactly(worker1,worker2);
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
        List<Worker> allWorkers = sut.getAllWorkers();
        for (Worker worker : allWorkers) {
            Map<LocalDate, Day> result = worker.returnWorkSchedule();
            assertThat(result).size().isEqualTo(3);
            assertThat(result).containsAllEntriesOf(expected);
        }

    }

    @Test
    public void checkPlanToJsonByJackson() throws Exception {
        // given
        LocalDate start = LocalDate.of(2017,1,1);
        LocalDate middle = LocalDate.of(2017,1,2);
        LocalDate end = LocalDate.of(2017,1,3);

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
        worker8.getDayMap().get(start).setStart(6);
        worker8.getDayMap().get(start).setEnd(14);
        worker8.getDayMap().get(middle).setStart(14);
        worker8.getDayMap().get(middle).setEnd(22);
        Worker worker9 = new Worker("Zaneta", "Gdanska");
        worker9.initializeWorkSchedule(middle, end);
        worker9.getDayMap().get(middle).setStart(6);
        worker9.getDayMap().get(middle).setEnd(14);
        worker9.getDayMap().get(end).setStart(14);
        worker9.getDayMap().get(end).setEnd(22);
        planWith2WorkersWithTheirOwnWorkingDayMap.addWorker(worker8);
        planWith2WorkersWithTheirOwnWorkingDayMap.addWorker(worker9);
        String expectedJsonPlanWith2WorkersWithTheirOwnWorkingDayMap = "{\"workerList\":[{\"name\":\"Szymon\",\"surname\":\"Gdynski\",\"dayMap\":[[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]},{\"name\":\"Zaneta\",\"surname\":\"Gdanska\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":6,\"end\":14}]]}]}";

        Plan planWith2WorkersAndThenAddingWorkingDays = new Plan();
        Worker worker10 = new Worker("Adam", "Schumacher");
        Worker worker11 = new Worker("Wladyslaw", "Kubica");
        planWith2WorkersAndThenAddingWorkingDays.addWorker(worker10);
        planWith2WorkersAndThenAddingWorkingDays.addWorker(worker11);
        planWith2WorkersAndThenAddingWorkingDays.initializeWorkSchedule(start, end);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker10)).getDayMap().get(start).setStart(6);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker10)).getDayMap().get(start).setEnd(14);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker10)).getDayMap().get(middle).setStart(6);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker10)).getDayMap().get(middle).setEnd(14);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker10)).getDayMap().get(end).setStart(14);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker10)).getDayMap().get(end).setEnd(22);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker11)).getDayMap().get(start).setStart(14);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker11)).getDayMap().get(start).setEnd(22);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker11)).getDayMap().get(middle).setStart(14);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker11)).getDayMap().get(middle).setEnd(22);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker11)).getDayMap().get(end).setStart(6);
        planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().get(planWith2WorkersAndThenAddingWorkingDays.getAllWorkers().indexOf(worker11)).getDayMap().get(end).setEnd(14);
        String expectedJsonPlanWith2WorkersAndThenAddingWorkingDays = "{\"workerList\":[{\"name\":\"Adam\",\"surname\":\"Schumacher\",\"dayMap\":[[\"2017-01-03\",{\"start\":14,\"end\":22}],[\"2017-01-02\",{\"start\":6,\"end\":14}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]},{\"name\":\"Wladyslaw\",\"surname\":\"Kubica\",\"dayMap\":[[\"2017-01-03\",{\"start\":6,\"end\":14}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":14,\"end\":22}]]}]}";
        ObjectMapper mapper = new ObjectMapper();
        // when
        String jsonPlanEmpty = mapper.writeValueAsString(planEmpty);
        String jsonPlanWith1Worker = mapper.writeValueAsString(planWith1Worker);
        String jsonPlanWith2Workers = mapper.writeValueAsString(planWith2Workers);
        String jsonPlanWith2WorkersAndInitialized = mapper.writeValueAsString(planWith2WorkersAndInitialized);
        String jsonPlanWith2WorkersWithTheirOwnInitializedDayMap = mapper.writeValueAsString(planWith2WorkersWithTheirOwnInitializedDayMap);
        String jsonPlanWith2WorkersWithTheirOwnWorkingDayMap = mapper.writeValueAsString(planWith2WorkersWithTheirOwnWorkingDayMap);
        String jsonPlanWith2WorkersAndThenAddingWorkingDays = mapper.writeValueAsString(planWith2WorkersAndThenAddingWorkingDays);
        // then
        assertThat(jsonPlanEmpty).isEqualTo(expectedJsonPlanEmpty);
        assertThat(jsonPlanWith1Worker).isEqualTo(expectedJsonPlanWith1Worker);
        assertThat(jsonPlanWith2Workers).isEqualTo(expectedJsonPlanWith2Workers);
        assertThat(jsonPlanWith2WorkersAndInitialized).isEqualTo(expectedJsonPlanWith2WorkersAndInitialized);
        assertThat(jsonPlanWith2WorkersWithTheirOwnInitializedDayMap).isEqualTo(expectedJsonPlanWith2WorkersWithTheirOwnInitializedDayMap);
        assertThat(jsonPlanWith2WorkersWithTheirOwnWorkingDayMap).isEqualTo(expectedJsonPlanWith2WorkersWithTheirOwnWorkingDayMap);
        assertThat(jsonPlanWith2WorkersAndThenAddingWorkingDays).isEqualTo(expectedJsonPlanWith2WorkersAndThenAddingWorkingDays);
    }
}