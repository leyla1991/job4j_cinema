package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Halls;
import ru.job4j.cinema.repository.HallsRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleHallsService implements HallsService {

    private final HallsRepository hallsRepository;

    public SimpleHallsService(HallsRepository sql2oHallsRepository) {
        this.hallsRepository = sql2oHallsRepository;
    }

    @Override
    public Optional<Halls> findById(int id) {
        return hallsRepository.findById(id);
    }

    @Override
    public Collection<Halls> findAll() {
        return hallsRepository.findAll();
    }
}
