package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Film;

import java.util.*;

public class MemoryFilmRepository implements FilmRepository {

    private static final MemoryFilmRepository INSTANCE = new MemoryFilmRepository();

    private int nextId = 1;

    private final Map<Integer, Film> films = new HashMap<>();

    private MemoryFilmRepository() {
        save(new Film(0, "Java horror", 120));
        save(new Film(0, "Javast", 110));
        save(new Film(0, "Java horror", 100));
        save(new Film(0, "Java и 7 гномов", 120));
        save(new Film(0, "Java horror: SQL", 120));
        save(new Film(0, "Javest", 120));

    }

    public static MemoryFilmRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public Film save(Film film) {
        film.setId(nextId++);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public void deleteById(int id) {
        films.remove(id);
    }

    @Override
    public boolean update(Film film) {
        return films.computeIfPresent(film.getId(),
                (id, oldFilm) -> new Film(oldFilm.getId(), film.getTitle(), film.getTime())) != null;
    }

    @Override
    public Optional<Film> findById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Collection<Film> findAll() {
        return films.values();
    }
}
