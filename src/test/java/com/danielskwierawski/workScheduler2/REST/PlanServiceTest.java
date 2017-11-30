package com.danielskwierawski.workScheduler2.REST;

import org.junit.Test;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanServiceTest {

    @Test
    public void checkGETWorkersWhenEmpty() throws Exception {
        // given
        delete("/workScheduler2/workers");
        String expectedJson = "";
        // when
        String json = get("/workScheduler2/workers").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkGETWorkers() throws Exception {
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
    public void checkGETWorkerPathParamWhenEmpty() throws Exception {
        // given
        delete("/workScheduler2/workers");
        String expectedJson = "";
        // when
        String json = get("/workScheduler2/worker/Daniel.Kowalski").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkGETWorkerPathParam() throws Exception {
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
    public void checkGETWorkerQueryParamWhenEmpty() throws Exception {
        // given
        delete("/workScheduler2/workers");
        String expectedJson = "";
        // when
        String json = get("/workScheduler2/worker?name=Daniel&surname=Kowalski").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }

    @Test
    public void checkGETWorkerQueryParam() throws Exception {
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
}