package com.danielskwierawski.workScheduler2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExperimentTest {

    @Test
    public void checkExperimentToJsonByJackson() throws Exception {
        // given
        Experiment experiment = new Experiment();
        experiment.setParameters();
        String expectedJsonExperiment = "{\"counter\":1,\"name\":\"Kowalski\",\"date\":\"2017-02-01\",\"day\":{\"start\":6,\"end\":14},\"dayMap\":[[\"2017-02-02\",{\"start\":7,\"end\":15}],[\"2017-02-01\",{\"start\":6,\"end\":14}]]}";
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        // when
        String jsonExperiment = mapper.writeValueAsString(experiment);
        System.out.println(jsonExperiment);
        // then
        assertThat(jsonExperiment).isEqualTo(expectedJsonExperiment);

    }

    @Test
    public void checkJsonToExperimentByJackson() throws Exception {
        // given
        String jsonExperiment = "{\"counter\":1,\"name\":\"Kowalski\",\"date\":\"2017-02-01\",\"day\":{\"start\":6,\"end\":14},\"dayMap\":[[\"2017-02-02\",{\"start\":7,\"end\":15}],[\"2017-02-01\",{\"start\":6,\"end\":14}]]}";
        Experiment expectedExperiment = new Experiment();
        expectedExperiment.setParameters();
        ObjectMapper mapper = new ObjectMapper();
        // when
        Experiment experiment = mapper.readValue(jsonExperiment, Experiment.class);
        // then
        assertThat(experiment).isEqualTo(expectedExperiment);
    }
}