package com.danielskwierawski.workScheduler2.REST;

import com.danielskwierawski.workScheduler2.model.Day;
import com.danielskwierawski.workScheduler2.model.Plan;
import com.danielskwierawski.workScheduler2.model.Worker;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;

@Path("/")
public class PlanService {

    @Inject
    private Plan plan;

    @GET
    @Path("/hello/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sayHello(@PathParam("name") String name) {
        return Response.ok(name).build();
    }

    @GET
    @Path("/workers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWorkers() {
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
        plan.addWorker(worker1);
        plan.addWorker(worker2);
        plan.addWorker(worker3);

        List<Worker> workerList = plan.getAllWorkers();

        if (workerList.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(workerList).build();
    }

    @GET
    @Path("/worker")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorker(@QueryParam("name") String name, @QueryParam("surname") String surname) {
        List<Worker> workerList = plan.getAllWorkers();

        for (Worker worker : workerList) {
            if (worker.getName().equals(name) && worker.getSurname().equals(surname)) {
                return Response.ok(worker).build();
            }
        }
        return Response.noContent().build();
    }

    @POST
    @Path("/worker")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addWorker(Worker worker) {

        Worker newWorker = new Worker(worker.getName(), worker.getSurname());

        plan.addWorker(newWorker);

        return getAllWorkers();
    }

    @POST
    @Path("/day")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDay(Day day) {
        Day newDay = day;

        return Response.ok(newDay).build();
    }


}
