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
    @Path("/addInitial")
    public Response addInitial() {
        LocalDate firstDay = LocalDate.of(2017, 1, 1);
        LocalDate secondDay = LocalDate.of(2017, 1, 2);
        LocalDate lastDay = LocalDate.of(2017, 1, 3);
        Worker worker1 = new Worker("Daniel", "Kowalski");
        Worker worker2 = new Worker("Zbigniew", "Wisniewski");
        worker2.initializeWorkSchedule(firstDay, lastDay);
        Worker worker3 = new Worker("Krzysztof", "Pienkowski");
        worker3.initializeWorkSchedule(firstDay, lastDay);
        worker3.getDayMap().get(firstDay).setStart(6);
        worker3.getDayMap().get(firstDay).setEnd(14);
        worker3.getDayMap().get(secondDay).setStart(14);
        worker3.getDayMap().get(secondDay).setEnd(22);
        plan.addWorker(worker1);
        plan.addWorker(worker2);
        plan.addWorker(worker3);

        return Response.ok().build();
    }

    @GET
    @Path("/workers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllWorkers() {

        List<Worker> workerList = plan.getWorkerList();

        if (workerList.isEmpty()) {
            return Response.noContent().build();
        }

        return Response.ok(workerList).build();
    }

    @GET
    @Path("/worker")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkerQueryParam(@QueryParam("name") String name, @QueryParam("surname") String surname) {
        List<Worker> workerList = plan.getWorkerList();

        for (Worker worker : workerList) {
            if (worker.getName().equals(name) && worker.getSurname().equals(surname)) {
                return Response.ok(worker).build();
            }
        }
        return Response.noContent().build();
    }

    @GET
    @Path("/worker/{name}.{surname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWorkerPathParam(@PathParam("name") String name, @PathParam("surname") String surname) {
        List<Worker> workerList = plan.getWorkerList();

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
        for (Worker iterator: plan.getWorkerList()) {
            if (iterator.getName().equals(worker.getName()) && iterator.getSurname().equals(worker.getSurname())) {
                return Response.status(Response.Status.CONFLICT).build();
            }
        }
        plan.addWorker(worker);
        return getAllWorkers();
    }

    @PUT
    @Path("/worker")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateWorker(Worker worker) {
        for (Worker iterator : plan.getWorkerList()) {
            if (iterator.getName().equals(worker.getName()) && iterator.getSurname().equals(worker.getSurname())) {
                iterator.setDayMap(worker.getDayMap());
                return getAllWorkers();
            }
        }
        plan.addWorker(worker);
        return getAllWorkers();
    }

    @DELETE
    @Path("/worker")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWorkerQueryParam(@QueryParam("name") String name, @QueryParam("surname") String surname) {
        List<Worker> workerList = plan.getWorkerList();

        for (Worker worker : workerList) {
            if (worker.getName().equals(name) && worker.getSurname().equals(surname)) {
                workerList.remove(worker);
                return getAllWorkers();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/worker/{name}.{surname}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteWorkerPathParam(@PathParam("name") String name, @PathParam("surname") String surname) {
        List<Worker> workerList = plan.getWorkerList();

        for (Worker worker : workerList) {
            if (worker.getName().equals(name) && worker.getSurname().equals(surname)) {
                workerList.remove(worker);
                return getAllWorkers();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/workers")
    public Response deleteAllWorkers() {
        plan.removeAllWorkers();
        return Response.ok().build();
    }
}
