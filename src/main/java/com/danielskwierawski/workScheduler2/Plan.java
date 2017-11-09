package com.danielskwierawski.workScheduler2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Plan {
    private List<Worker> workerList = new ArrayList<>();
    public static final int defaultWorkTime = 8;

    public void addWorker(Worker worker) {
        workerList.add(worker);
    }

    public List<Worker> getAllWorkers() {
        return workerList;
    }

    public void initializeWorkSchedule(LocalDate start, LocalDate end) {
        for (Worker worker : workerList) {
            worker.initializeWorkSchedule(start, end);
        }
    }
}

