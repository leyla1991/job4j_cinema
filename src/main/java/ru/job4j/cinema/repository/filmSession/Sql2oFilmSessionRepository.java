package ru.job4j.cinema.repository.filmSession;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository {

    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        try (var connection = sql2o.open()) {
            var sql = """
                    SELECT * FROM film_sessions
                    WHERE id = :id
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("id", id);
            var filmSession = query.setColumnMappings(FilmSession.COLUMN_MAPPING)
                    .executeAndFetchFirst(FilmSession.class);
            return Optional.ofNullable(filmSession);
        }
    }

    @Override
    public Collection<FilmSession> findAll() {
        try (var connection = sql2o.open()) {
            var sql = connection.createQuery("SELECT * FROM film_sessions");
             return sql.setColumnMappings(FilmSession.COLUMN_MAPPING).executeAndFetch(FilmSession.class);
        }
    }
}
