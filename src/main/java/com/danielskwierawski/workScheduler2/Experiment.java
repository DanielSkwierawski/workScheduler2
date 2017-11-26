package com.danielskwierawski.workScheduler2;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@ToString
@EqualsAndHashCode
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
    @JsonDeserialize(using = MapDeserializer.class, keyAs = LocalDate.class, contentAs = Day.class)
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
