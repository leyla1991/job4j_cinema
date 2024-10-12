package ru.job4j.cinema.repository.halls;

import ru.job4j.cinema.model.Halls;

import java.util.Collection;
import java.util.Optional;

public interface HallsRepository {

    Optional<Halls> findById(int id);

    Collection<Halls> findAll();
}
