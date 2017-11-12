package com.danielskwierawski.workScheduler2;

import com.danielskwierawski.workScheduler2.model.Plan;
import com.danielskwierawski.workScheduler2.model.Worker;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

public class Jsonator {

    public static void main(String[] args) {
        LocalDate start = LocalDate.of(2017, 1, 1);
        LocalDate middle = LocalDate.of(2017, 1, 2);
        LocalDate end = LocalDate.of(2017, 1, 3);

        Worker worker1 = new Worker("Daniel", "Kowalski");

        Worker worker2 = new Worker("Zbigniew", "Wisniewski");
        worker2.initializeWorkSchedule(start, end);
        worker2.getDayMap().get(start).setStart(14);
        worker2.getDayMap().get(start).setEnd(22);
        worker2.getDayMap().get(middle).setStart(6);
        worker2.getDayMap().get(middle).setEnd(14);

        Worker worker3 = new Worker("Krzysztof", "Pienkowski");
        worker3.initializeWorkSchedule(start, end);
        worker3.getDayMap().get(start).setStart(6);
        worker3.getDayMap().get(start).setEnd(14);
        worker3.getDayMap().get(middle).setStart(14);
        worker3.getDayMap().get(middle).setEnd(22);

        Plan plan1 = new Plan();
        plan1.addWorker(worker1);
        plan1.addWorker(worker2);
        plan1.addWorker(worker3);

        Gson gson = new Gson();
        String jsonPlan1 = gson.toJson(plan1);

        System.out.println("jsonPlan1: " + jsonPlan1);

        try {
            PrintWriter printWriter = new PrintWriter("src/main/resources/file1.json", "UTF-8");
            printWriter.print(jsonPlan1);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
