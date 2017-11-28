package com.danielskwierawski.workScheduler2;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberOfDaysTest {

    @Test
    public void getNumberOfDaysInGivenMonth() throws Exception {
        // given
        LocalDate January = LocalDate.of(2017, 1, 01);
        LocalDate February = LocalDate.of(2017, 2, 01);
        LocalDate March = LocalDate.of(2017, 3, 01);
        LocalDate April = LocalDate.of(2017, 4, 01);
        LocalDate May = LocalDate.of(2017, 5, 01);
        LocalDate June = LocalDate.of(2017, 6, 01);
        LocalDate July = LocalDate.of(2017, 7, 01);
        LocalDate August = LocalDate.of(2017, 8, 01);
        LocalDate September = LocalDate.of(2017, 9, 01);
        LocalDate October = LocalDate.of(2017, 10, 01);
        LocalDate November = LocalDate.of(2017, 11, 01);
        LocalDate December = LocalDate.of(2017, 12, 01);
        LocalDate FebruaryCommonYear = LocalDate.of(2018, 2, 01);
        LocalDate FebruaryCommonYear2 = LocalDate.of(2019, 2, 01);
        LocalDate FebruaryLeapYear = LocalDate.of(2020, 2, 01);
        NumberOfDays sut = new NumberOfDays();
        // when
        Integer resultJanuary = sut.getNumberOfDays(January);
        Integer resultFebruary = sut.getNumberOfDays(February);
        Integer resultMarch = sut.getNumberOfDays(March);
        Integer resultApril = sut.getNumberOfDays(April);
        Integer resultMay = sut.getNumberOfDays(May);
        Integer resultJune = sut.getNumberOfDays(June);
        Integer resultJuly = sut.getNumberOfDays(July);
        Integer resultAugust = sut.getNumberOfDays(August);
        Integer resultSeptember = sut.getNumberOfDays(September);
        Integer resultOctober = sut.getNumberOfDays(October);
        Integer resultNovember = sut.getNumberOfDays(November);
        Integer resultDecember = sut.getNumberOfDays(December);
        Integer resultFebruaryCommonYear = sut.getNumberOfDays(FebruaryCommonYear);
        Integer resultFebruaryCommonYear2 = sut.getNumberOfDays(FebruaryCommonYear2);
        Integer resultFebruaryLeapYear = sut.getNumberOfDays(FebruaryLeapYear);
        // then
        assertThat(resultJanuary).isEqualTo(31);
        assertThat(resultFebruary).isEqualTo(28);
        assertThat(resultMarch).isEqualTo(31);
        assertThat(resultApril).isEqualTo(30);
        assertThat(resultMay).isEqualTo(31);
        assertThat(resultJune).isEqualTo(30);
        assertThat(resultJuly).isEqualTo(31);
        assertThat(resultAugust).isEqualTo(31);
        assertThat(resultSeptember).isEqualTo(30);
        assertThat(resultOctober).isEqualTo(31);
        assertThat(resultNovember).isEqualTo(30);
        assertThat(resultDecember).isEqualTo(31);
        assertThat(resultFebruaryCommonYear).isEqualTo(28);
        assertThat(resultFebruaryCommonYear2).isEqualTo(28);
        assertThat(resultFebruaryLeapYear).isEqualTo(29);
    }
}