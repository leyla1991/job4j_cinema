package ru.job4j.cinema.service.ticket;

import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

public interface TicketService {

    Optional<Ticket> save(Ticket ticket);

    void deleteById(int id);

    Optional<Ticket> findById(int id);

    Collection<Ticket> findByUserId(int id);

    Collection<Ticket> findAll();
}
