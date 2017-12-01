package com.danielskwierawski.workScheduler2.REST;

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
        for (Worker iterator : plan.getWorkerList()) {
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
