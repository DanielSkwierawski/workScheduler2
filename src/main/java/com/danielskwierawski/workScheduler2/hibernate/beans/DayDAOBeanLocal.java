package com.danielskwierawski.workScheduler2.hibernate.beans;

import com.danielskwierawski.workScheduler2.model.Day;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DayDAOBeanLocal {
    void addDay(Day day);

    void updateDay(Day day);

    void deleteDay(Integer id);

    Day findDayById(Integer id);

    List<Day> findAllDays();
}
