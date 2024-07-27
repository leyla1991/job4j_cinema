package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Schedule;

import java.time.LocalDateTime;
import java.util.*;

public class MemoryScheduleRepository implements ScheduleRepository {

    private static final MemoryScheduleRepository INSTANCE =
            new MemoryScheduleRepository();

    private int nextId = 1;

    private final Map<Integer, Schedule> schedules = new HashMap<>();

    private MemoryScheduleRepository() {
        save(new Schedule(0, "It", "scrim", LocalDateTime.now()));
        save(new Schedule(0, "Shark", "scare", LocalDateTime.now()));
        save(new Schedule(0, "Cinderella", "child", LocalDateTime.now()));
        save(new Schedule(0, "Gift", "christmas", LocalDateTime.now()));
    }

    public static MemoryScheduleRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Schedule save(Schedule schedule) {
        schedule.setId(nextId++);
        schedules.put(nextId, schedule);
        return schedule;
    }

    @Override
    public void deleteById(int id) {
        schedules.remove(id);
    }

    @Override
    public boolean update(Schedule schedule) {
        return schedules.computeIfPresent(schedule.getId(),
                (id, oldSchedule) -> new Schedule(oldSchedule.getId(), schedule.getName(), schedule.getDescription(), schedule.getCreate())) != null;
    }

    @Override
    public Optional<Schedule> findById(int id) {
        return Optional.ofNullable(schedules.get(id));
    }

    @Override
    public Collection<Schedule> findAll() {
        return schedules.values();
    }
}
