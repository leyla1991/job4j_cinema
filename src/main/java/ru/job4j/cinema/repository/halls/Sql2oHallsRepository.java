package ru.job4j.cinema.repository.halls;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Halls;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oHallsRepository implements HallsRepository {

    private final Sql2o sql2o;

    public Sql2oHallsRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Halls> findById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM halls WHERE id = :id");
            var halls = query.addParameter("id", id)
                    .setColumnMappings(Halls.COLUMN_MAPPING)
                    .executeAndFetchFirst(Halls.class);
            return Optional.ofNullable(halls);
        }
    }

    @Override
    public Collection<Halls> findAll() {
        try (var connection = sql2o.open()) {
           var query = connection.createQuery("SELECT * FROM halls");
           return query.setColumnMappings(Halls.COLUMN_MAPPING).executeAndFetch(Halls.class);
        }
    }
}
