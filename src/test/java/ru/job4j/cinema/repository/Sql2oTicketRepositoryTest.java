package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.ticket.Sql2oTicketRepository;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oTicketRepositoryTest {

    private static Sql2oTicketRepository sql2oTicketRepository;

    @BeforeAll
    public static void init() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);
    }

    @AfterEach
    public void deleteById() {
        var tickets = sql2oTicketRepository.findAll();
        for (var ticket : tickets) {
            sql2oTicketRepository.deleteById(ticket.getId());
        }
    }

    @Test
    public void whenSaveAndFoundOneTicket() {
        var ticket1 = new Ticket(1, 1, 1, 1, 3);
        var actualExpected = sql2oTicketRepository.save(ticket1);
        var expectedTicket = sql2oTicketRepository.findById(ticket1.getId());
        assertThat(actualExpected).isEqualTo(expectedTicket);
    }

    @Test
    public void whenSaveManyAndShowTickets() {

        var ticket1 = sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, 1));
        var ticket2 = sql2oTicketRepository.save(new Ticket(2, 1, 1, 2, 1));
        var ticket3 = sql2oTicketRepository.save(new Ticket(3, 1, 1, 3, 1));

        var actualExpected = List.of(ticket1.get(), ticket2.get(), ticket3.get());
        var expectedTickets = sql2oTicketRepository.findAll();

        assertThat(actualExpected).isEqualTo(expectedTickets);
    }

    @Test
    public void whenTwoUserBuyOneTicket() {

        var user1 = new User("pass", "email", "name", 1);
        var user2 = new User("pass", "email", "name", 2);

        sql2oTicketRepository.save(new Ticket(1, 1, 1, 1, user1.getId()));

        var actualTicket = sql2oTicketRepository.save(new Ticket(78, 1, 1, 1, user2.getId()));
        var expectedTicket = Optional.empty();

        assertThat(actualTicket).isEqualTo(expectedTicket);
    }
}
