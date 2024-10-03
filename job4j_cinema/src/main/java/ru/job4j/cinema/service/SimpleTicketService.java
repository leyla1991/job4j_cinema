package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class SimpleTicketService implements TicketService {

    private final TicketRepository ticketRepository;

    public SimpleTicketService(TicketRepository sql2oTicketRepository) {
        this.ticketRepository = sql2oTicketRepository;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        var userS = ticketRepository.findByUserId(ticket.getUserId());
       return !userS.isEmpty() ? ticketRepository.save(ticket) : Optional.empty();
    }

    @Override
    public void deleteById(int id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Optional<Ticket>
    findById(int id) {
        return ticketRepository.findById(id);
    }

    @Override
    public Collection<Ticket> findByUserId(int id) {
        return ticketRepository.findByUserId(id);
    }

    @Override
    public Collection<Ticket> findAll() {
        return ticketRepository.findAll();
    }
}
