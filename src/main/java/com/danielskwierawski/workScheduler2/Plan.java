package com.danielskwierawski.workScheduler2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
public class Plan {
    @JsonProperty
    @Getter
    private List<Worker> workerList = new ArrayList<>();
    public static final int DEFAULT_WORKING_TIME = 8;
    public static final int MIN_ALLOWED_START_WORKING = 6;
    public static final int MAX_ALLOWED_END_WORKING = 22;

    public void addWorker(Worker worker) {
        workerList.add(worker);
    }

    public void initializeWorkSchedule(LocalDate start, LocalDate end) {
        for (Worker worker : workerList) {
            worker.initializeWorkSchedule(start, end);
        }
    }
}

