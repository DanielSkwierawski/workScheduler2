package com.danielskwierawski.workScheduler2.json;

import com.danielskwierawski.workScheduler2.model.Day;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class MapSerializer extends JsonSerializer<Map<LocalDate, Day>> {

    @Override
    public void serialize(Map<LocalDate, Day> value, JsonGenerator gen, SerializerProvider sp) throws IOException, JsonProcessingException {
        gen.writeStartArray();
        for (Map.Entry<LocalDate, Day> entry : value.entrySet()) {
            gen.writeStartArray();
            gen.writeObject(entry.getKey().format(DateTimeFormatter.ISO_LOCAL_DATE));
            gen.writeObject(entry.getValue());
            gen.writeEndArray();
        }
        gen.writeEndArray();
    }

}
