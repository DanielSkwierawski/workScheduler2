package com.danielskwierawski.workScheduler2;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PlanTest {

    Plan sut;

    @Test
    public void name() throws Exception {
        // given
        sut = new Plan();
        Worker worker1 = new Worker("Artur", "Kowalski");
        Worker worker2 = new Worker("Zbigniew", "Wisniewski");
        Worker worker3 = new Worker("Mariusz", "Kowalski");
        List<Worker> workerList = new ArrayList<>();
        workerList.add(worker1);
        workerList.add(worker2);
        // when
        sut.addWorker(worker1);
        sut.addWorker(worker2);
        // then
        assertThat(sut.getAllWorkers()).containsExactly(worker1,worker2);
    }
}