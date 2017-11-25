package com.danielskwierawski.workScheduler2;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ToString
public class Experiment {

    @Getter
    @Setter
    private Integer counter;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @Getter
    @Setter
    private Day day;

    @Getter
    @Setter
    @JsonSerialize(using = MapSerializer.class)
//    @JsonDeserialize(using = ArrayToMapDeserializer.class, keyAs = Foo.class, contentAs = Bar.class)
    private Map<LocalDate, Day> dayMap;

    public void setParameters() {
        counter = 1;
        name = "Kowalski";
        date = LocalDate.of(2017, 2, 1);
        day = new Day(6);
        dayMap = new HashMap<LocalDate, Day>();
        dayMap.put(date, day);
        LocalDate date2 = LocalDate.of(2017, 2, 2);
        Day day2 = new Day(7);
        dayMap.put(date2, day2);
    }
}
