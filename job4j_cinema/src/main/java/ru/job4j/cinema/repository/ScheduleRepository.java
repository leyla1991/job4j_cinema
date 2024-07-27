package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Schedule;

import java.util.Collection;
import java.util.Optional;

public interface ScheduleRepository {

    Schedule save(Schedule schedule);

    void deleteById(int id);

    boolean update(Schedule schedule);

    Optional<Schedule> findById(int id);

    Collection<Schedule> findAll();
}
