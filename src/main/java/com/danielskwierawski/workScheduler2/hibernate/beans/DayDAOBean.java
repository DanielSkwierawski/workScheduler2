package com.danielskwierawski.workScheduler2.hibernate.beans;

import com.danielskwierawski.workScheduler2.model.Day;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class DayDAOBean implements DayDAOBeanLocal{

    @PersistenceContext(name = "conferencesUnit")
    EntityManager em;


    @Override
    public void addDay(Day day) {
        em.persist(day);
    }

    @Override
    public void updateDay(Day day) {
        em.merge(day);
    }

    @Override
    public void deleteDay(Integer id) {
        Day day = em.find(Day.class, id);
        if (day != null) {
            em.remove(day);
        }
    }

    @Override
    public Day findDayById(Integer id) {
        return em.find(Day.class, id);
    }

    @Override
    public List<Day> findAllDays() {
        Query q = em.createNamedQuery("com.danielskwierawski.workScheduler2.model.Day.findAll");
        return q.getResultList();
    }
}
