package com.danielskwierawski.workScheduler2.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ProgramDeserializer extends JsonDeserializer<Worker> {
    @Override
    public Worker deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        final String name = node.get("name").asText();
        final String surname = node.get("surname").asText();

        Worker worker = new Worker(name, surname);
        return worker;
    }


}