package ru.job4j.cinema.repository.ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.user.Sql2oUserRepository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(Sql2oUserRepository.class.getName());

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (var connection = sql2o.open()) {
            var sql = """ 
                    INSERT INTO tickets(session_id, row_number, place_number, user_id)
                    VALUES (:session_id, :row_number, :place_number, :user_id)
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("session_id", ticket.getSessionId())
                    .addParameter("row_number", ticket.getRowNumber())
                    .addParameter("place_number", ticket.getPlaceNumber())
                    .addParameter("user_id", ticket.getUserId());
            int generatedId = query.executeUpdate().getKey(Integer.class);
            ticket.setId(generatedId);
            return Optional.of(ticket);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(int id) {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("DELETE FROM tickets WHERE id = :id");
            query.addParameter("id", id);
            query.executeUpdate();
        }
    }

    @Override
    public Optional<Ticket> findById(int id) {
        try (var connection = sql2o.open()) {
            var sql = """
                    SELECT * FROM tickets
                    WHERE id = :id
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("id", id);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetchFirst(Ticket.class);
            return Optional.ofNullable(ticket);
        }
    }

    @Override
    public Collection<Ticket> findByUserId(int id) {
        try (var connection = sql2o.open()) {
            var sql = """
                    SELECT * FROM tickets
                    WHERE user_id = :user_id
                    """;
            var query = connection.createQuery(sql, true)
                    .addParameter("user_id", id);
            var ticket = query.setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetch(Ticket.class);
            return ticket;
        }
    }

    @Override
    public Collection<Ticket> findAll() {
        try (var connection = sql2o.open()) {
            var query = connection.createQuery("SELECT * FROM tickets");
            return query.setColumnMappings(Ticket.COLUMN_MAPPING).executeAndFetch(Ticket.class);
        }
    }
}
