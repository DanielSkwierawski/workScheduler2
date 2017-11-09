package com.danielskwierawski.workScheduler2;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkerTest {

    @Test
    public void checkCreationOfWorker() throws Exception {
        // given
        Worker sut;
        String name = "Zbigniew";
        String surname = "Kowalski";
        // when
        sut = new Worker(name, surname);
        // then
        assertThat(sut.getName()).isEqualTo(name);
        assertThat(sut.getSurname()).isEqualTo(surname);
    }
}