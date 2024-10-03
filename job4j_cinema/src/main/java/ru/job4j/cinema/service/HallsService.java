package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Halls;

import java.util.Collection;
import java.util.Optional;

public interface HallsService {

    Optional<Halls> findById(int id);

    Collection<Halls> findAll();
}
