package com.danielskwierawski.workScheduler2.REST;

import org.junit.Test;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanServiceTest {

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
    public void checkGETWorkersWhenEmpty() throws Exception {
        // given
        delete("/workScheduler2/workers");
        String expectedJson = "";
        // when
        String json = get("/workScheduler2/workers").asString();
        // then
        assertThat(json).isEqualTo(expectedJson);
    }
}