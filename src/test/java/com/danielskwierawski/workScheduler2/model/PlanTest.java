package com.danielskwierawski.workScheduler2.model;

import com.danielskwierawski.workScheduler2.model.Day;
import com.danielskwierawski.workScheduler2.model.Plan;
import com.danielskwierawski.workScheduler2.model.Worker;
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
}