package com.danielskwierawski.workScheduler2.REST;

import com.danielskwierawski.workScheduler2.model.Worker;
import org.junit.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanServiceTest {

    @Test
    public void checkGETWorkersWhenEmptyReturnsEmptyString() throws Exception {
        // given
        delete("/workScheduler2/workers");
        String expectedJson = "";
        // when
        String json = get("/workScheduler2/workers").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkGETWorkersWhenEmptyReturns204() throws Exception {
        delete("/workScheduler2/workers");
        given().when().get("/workScheduler2/workers").then().statusCode(204);
    }

    @Test
    public void checkGETWorkersReturnsJSONWithWorkers() throws Exception {
        // given
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        String expectedJson = "[{\"name\":\"Daniel\",\"surname\":\"Kowalski\",\"dayMap\":[]},{\"name\":\"Zbigniew\",\"surname\":\"Wisniewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Krzysztof\",\"surname\":\"Pienkowski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]}]";
        // when
        String json = get("/workScheduler2/workers").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkGETWorkersReturns200() throws Exception {
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        given().when().get("/workScheduler2/workers").then().statusCode(200);
    }

    @Test
    public void checkGETWorkerPathParamWhenEmptyReturnsEmptyString() throws Exception {
        // given
        delete("/workScheduler2/workers");
        String expectedJson = "";
        // when
        String json = get("/workScheduler2/worker/Daniel.Kowalski").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkGETWorkerPathParamWhenEmptyReturns204() throws Exception {
        delete("/workScheduler2/workers");
        given().when().get("/workScheduler2/worker/Daniel.Kowalski").then().statusCode(204);
    }

    @Test
    public void checkGETWorkerPathParamReturnsJSONWithParticularWorker() throws Exception {
        // given
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        String expectedJsonDanielKowalski = "{\"name\":\"Daniel\",\"surname\":\"Kowalski\",\"dayMap\":[]}";
        String expectedJsonZbigniewWisniewski = "{\"name\":\"Zbigniew\",\"surname\":\"Wisniewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]}";
        String expectedJsonKrzysztofPienkowski = "{\"name\":\"Krzysztof\",\"surname\":\"Pienkowski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]}";
        // when
        String jsonDanielKowalski = get("/workScheduler2/worker/Daniel.Kowalski").asString();
        String jsonZbigniewWisniewski = get("/workScheduler2/worker/Zbigniew.Wisniewski").asString();
        String jsonKrzysztofPienkowski = get("/workScheduler2/worker/Krzysztof.Pienkowski").asString();
        // then
        assertThat(jsonDanielKowalski).isEqualTo(expectedJsonDanielKowalski);
        assertThat(jsonZbigniewWisniewski).isEqualTo(expectedJsonZbigniewWisniewski);
        assertThat(jsonKrzysztofPienkowski).isEqualTo(expectedJsonKrzysztofPienkowski);
    }

    @Test
    public void checkGETWorkerPathParamReturns200() throws Exception {
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        given().when().get("/workScheduler2/worker/Daniel.Kowalski").then().statusCode(200);
        given().when().get("/workScheduler2/worker/Zbigniew.Wisniewski").then().statusCode(200);
        given().when().get("/workScheduler2/worker/Krzysztof.Pienkowski").then().statusCode(200);
    }

    @Test
    public void checkGETWorkerQueryParamWhenEmptyReturnsEmptyString() throws Exception {
        // given
        delete("/workScheduler2/workers");
        String expectedJson = "";
        // when
        String json = get("/workScheduler2/worker?name=Daniel&surname=Kowalski").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkGETWorkerQueryParamWhenEmptyReturns204() throws Exception {
        delete("/workScheduler2/workers");
        given().when().get("/workScheduler2/worker?name=Daniel&surname=Kowalski").then().statusCode(204);
    }

    @Test
    public void checkGETWorkerQueryParamReturnsJSONWithParticularWorker() throws Exception {
        // given
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        String expectedJsonDanielKowalski = "{\"name\":\"Daniel\",\"surname\":\"Kowalski\",\"dayMap\":[]}";
        String expectedJsonZbigniewWisniewski = "{\"name\":\"Zbigniew\",\"surname\":\"Wisniewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]}";
        String expectedJsonKrzysztofPienkowski = "{\"name\":\"Krzysztof\",\"surname\":\"Pienkowski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]}";
        // when
        String jsonDanielKowalski = get("/workScheduler2/worker?name=Daniel&surname=Kowalski").asString();
        String jsonZbigniewWisniewski = get("/workScheduler2/worker?name=Zbigniew&surname=Wisniewski").asString();
        String jsonKrzysztofPienkowski = get("/workScheduler2/worker?name=Krzysztof&surname=Pienkowski").asString();
        // then
        assertThat(jsonDanielKowalski).isEqualTo(expectedJsonDanielKowalski);
        assertThat(jsonZbigniewWisniewski).isEqualTo(expectedJsonZbigniewWisniewski);
        assertThat(jsonKrzysztofPienkowski).isEqualTo(expectedJsonKrzysztofPienkowski);
    }

    @Test
    public void checkGETWorkerQueryParamReturns200() throws Exception {
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        given().when().get("/workScheduler2/worker?name=Daniel&surname=Kowalski").then().statusCode(200);
        given().when().get("/workScheduler2/worker?name=Zbigniew&surname=Wisniewski").then().statusCode(200);
        given().when().get("/workScheduler2/worker?name=Krzysztof&surname=Pienkowski").then().statusCode(200);
    }

    @Test
    public void checkDeleteAllWorkersReturns200() throws Exception {
        given().when().delete("/workScheduler2/workers").then().statusCode(200);
    }

    @Test
    public void checkAfterDeleteAllWorkersThereAreNoWorker() throws Exception {
        // given
        get("/workScheduler2/addInitial");
        // when
        delete("/workScheduler2/workers");
        // then
        given().when().get("/workScheduler2/workers").then().statusCode(204);
        String expectedJson = "";
        String json = get("/workScheduler2/workers").asString();
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkDELETEWorkerPathParamWhenNoWorkerReturns404NotFound() throws Exception {
        delete("/workScheduler2/workers");
        given().when().delete("/workScheduler2/worker/Daniel.Kowalski").then().statusCode(404);
    }

    @Test
    public void AfterDELETEWorkerPathParamThereIsNoParticularWorker() throws Exception {
        // given
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        // when
        delete("/workScheduler2/worker/Daniel.Kowalski");
        // then
        String expectedJson = "[{\"name\":\"Zbigniew\",\"surname\":\"Wisniewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Krzysztof\",\"surname\":\"Pienkowski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]}]";
        String json = get("/workScheduler2/workers").asString();
        assertThat(json).isEqualTo(expectedJson);

    }

    @Test
    public void checkDELETEWorkerPathParamReturns200WhenThereIsOtherWorker() throws Exception {
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        given().when().delete("/workScheduler2/worker/Daniel.Kowalski").then().statusCode(200);
    }

    @Test
    public void checkDELETEWorkerPathParamReturnsJSONWithWorkersWhenThereIsOtherWorker() throws Exception {
        // given
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        String expectedJson = "[{\"name\":\"Zbigniew\",\"surname\":\"Wisniewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Krzysztof\",\"surname\":\"Pienkowski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]}]";
        // when
        String json = delete("/workScheduler2/worker/Daniel.Kowalski").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkDELETEWorkerPathParamReturns204WhenThereIsNoWorker() throws Exception {
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        delete("/workScheduler2/worker/Zbigniew.Wisniewski");
        delete("/workScheduler2/worker/Krzysztof.Pienkowski");
        given().when().delete("/workScheduler2/worker/Daniel.Kowalski").then().statusCode(204);
    }

    @Test
    public void checkDELETEWorkerPathParamReturnsEmptyStringWhenThereIsNoWorker() throws Exception {
        // given
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        delete("/workScheduler2/worker/Zbigniew.Wisniewski");
        delete("/workScheduler2/worker/Krzysztof.Pienkowski");
        String expectedJson = "";
        // when
        String json = delete("/workScheduler2/worker/Daniel.Kowalski").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkDELETEWorkerQueryParamWhenNoWorkerReturns404NotFound() throws Exception {
        delete("/workScheduler2/workers");
        given().when().delete("/workScheduler2/worker?name=Daniel&surname=Kowalski").then().statusCode(404);
    }

    @Test
    public void AfterDELETEWorkerQueryParamThereIsNoParticularWorker() throws Exception {
        // given
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        // when
        delete("/workScheduler2/worker?name=Daniel&surname=Kowalski");
        // then
        String expectedJson = "[{\"name\":\"Zbigniew\",\"surname\":\"Wisniewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Krzysztof\",\"surname\":\"Pienkowski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]}]";
        String json = get("/workScheduler2/workers").asString();
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkDELETEWorkerQueryParamReturns200WhenThereIsOtherWorker() throws Exception {
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        given().when().delete("/workScheduler2/worker?name=Daniel&surname=Kowalski").then().statusCode(200);
    }

    @Test
    public void checkDELETEWorkerQueryParamReturnsJSONWithWorkersWhenThereIsOtherWorker() throws Exception {
        // given
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        String expectedJson = "[{\"name\":\"Zbigniew\",\"surname\":\"Wisniewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Krzysztof\",\"surname\":\"Pienkowski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]}]";
        // when
        String json = delete("/workScheduler2/worker?name=Daniel&surname=Kowalski").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkDELETEWorkerQueryParamReturns204WhenThereIsNoWorker() throws Exception {
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        delete("/workScheduler2/worker?name=Zbigniew&surname=Wisniewski");
        delete("/workScheduler2/worker?name=Krzysztof&surname=Pienkowski");
        given().when().delete("/workScheduler2/worker?name=Daniel&surname=Kowalski").then().statusCode(204);
    }

    @Test
    public void checkDELETEWorkerQueryParamReturnsEmptyStringWhenThereIsNoWorker() throws Exception {
        // given
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        delete("/workScheduler2/worker?name=Zbigniew&surname=Wisniewski");
        delete("/workScheduler2/worker?name=Krzysztof&surname=Pienkowski");
        String expectedJson = "";
        // when
        String json = delete("/workScheduler2/worker?name=Daniel&surname=Kowalski").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkPOSTWorkerReturns200() throws Exception {
        // given
        delete("/workScheduler2/workers");
        Worker worker = new Worker("Daniel", "Kowalski");
        given().contentType("application/json").body(worker).when().post("/workScheduler2/worker").then().statusCode(200);
    }

    @Test
    public void checkPOSTWorkerReturnsJSONWithWorkers() throws Exception {
        // given
        delete("/workScheduler2/workers");
        Worker worker = new Worker("Daniel", "Kowalski");
        String expectedJson = "[{\"name\":\"Daniel\",\"surname\":\"Kowalski\",\"dayMap\":[]}]";
        // when
        String json = given().contentType("application/json").body(worker).when().post("/workScheduler2/worker").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkPOSTWorkerReturns409WhenThereIsDuplication() throws Exception {
        delete("/workScheduler2/workers");
        Worker worker = new Worker("Daniel", "Kowalski");
        given().contentType("application/json").body(worker).when().post("/workScheduler2/worker");

        given().contentType("application/json").body(worker).when().post("/workScheduler2/worker").then().statusCode(409);
    }

    @Test
    public void checkPUTWorkerReturns200() throws Exception {
        Worker worker = new Worker("Daniel", "Kowalski");
        given().contentType("application/json").body(worker).when().put("/workScheduler2/worker").then().statusCode(200);
    }

    @Test
    public void checkPUTWorkerReturnsJSONWithWorkers() throws Exception {
        // given
        delete("/workScheduler2/workers");
        get("/workScheduler2/addInitial");
        Worker worker = new Worker("Mariusz", "Adamski");
        String expectedJson = "[{\"name\":\"Daniel\",\"surname\":\"Kowalski\",\"dayMap\":[]},{\"name\":\"Zbigniew\",\"surname\":\"Wisniewski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":null,\"end\":null}],[\"2017-01-01\",{\"start\":null,\"end\":null}]]},{\"name\":\"Krzysztof\",\"surname\":\"Pienkowski\",\"dayMap\":[[\"2017-01-03\",{\"start\":null,\"end\":null}],[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]},{\"name\":\"Mariusz\",\"surname\":\"Adamski\",\"dayMap\":[]}]";
        // when
        String json = given().contentType("application/json").body(worker).when().put("/workScheduler2/worker").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkPUTWorkerAddNewWorker() throws Exception {
        // given
        delete("/workScheduler2/workers");
        Worker worker = new Worker("Daniel", "Kowalski");
        // when
        given().contentType("application/json").body(worker).when().put("/workScheduler2/worker");
        // then
        String expectedJson = "[{\"name\":\"Daniel\",\"surname\":\"Kowalski\",\"dayMap\":[]}]";
        String json = get("/workScheduler2/workers").asString();
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkPUTWorkerModifyAlreadyExistedWorker() throws Exception {
        // given
        delete("/workScheduler2/workers");
        Worker worker = new Worker("Daniel", "Kowalski");
        given().contentType("application/json").body(worker).when().put("/workScheduler2/worker");

        LocalDate firstDay = LocalDate.of(2017, 1, 1);
        LocalDate secondDay = LocalDate.of(2017, 1, 2);
        worker.initializeWorkSchedule(firstDay, secondDay);
        worker.getDayMap().get(firstDay).setStart(6);
        worker.getDayMap().get(firstDay).setEnd(14);
        worker.getDayMap().get(secondDay).setStart(14);
        worker.getDayMap().get(secondDay).setEnd(22);
        // when
        given().contentType("application/json").body(worker).when().put("/workScheduler2/worker");
        // then
        String expectedJson = "[{\"name\":\"Daniel\",\"surname\":\"Kowalski\",\"dayMap\":[[\"2017-01-02\",{\"start\":14,\"end\":22}],[\"2017-01-01\",{\"start\":6,\"end\":14}]]}]";
        String json = get("/workScheduler2/workers").asString();
        assertThat(json).isEqualTo(expectedJson);
    }
}