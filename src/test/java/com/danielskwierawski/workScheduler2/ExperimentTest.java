package com.danielskwierawski.workScheduler2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class ExperimentTest {

    @Test
    public void checkExperimentToJsonByJackson() throws Exception {
        // given
        Experiment experiment = new Experiment();
        experiment.setParameters();
        ObjectMapper mapper = new ObjectMapper();
//        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        // when
        String jsonExperiment = mapper.writeValueAsString(experiment);
        System.out.println(jsonExperiment);
        // then

    }

    @Test
    public void checkJsonToExperimentByJackson() throws Exception {
        // given
//        String jsonExperiment = "";
        String jsonExperiment = "{\"counter\":1,\"name\":\"Kowalski\",\"date\":\"2017-02-01\"}";
        ObjectMapper mapper = new ObjectMapper();
        // when
        Experiment experiment = mapper.readValue(jsonExperiment, Experiment.class);
        // then
        System.out.println(experiment.toString());
        System.out.println(experiment);

    }
}