package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.schedules.FilmSessionService;
import ru.job4j.cinema.service.ticket.TicketService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/tickets")
@ThreadSafe
public class TicketController {

    private TicketService ticketService;

    private FilmSessionService service;

    public TicketController(TicketService ticketService, FilmSessionService service) {
        this.ticketService = ticketService;
        this.service = service;
    }

    @PostMapping("/buy")
    public String buyTicket(@ModelAttribute Ticket ticket, Model model, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        var optionalTicket = ticketService.save(ticket);
        if (optionalTicket.isEmpty()) {
            model.addAttribute("message", "Данные места заняты");
            return "errors/409";
        }
        model.addAttribute("ticket", ticket);
        model.addAttribute("film", service.findById(ticket.getSessionId()));
        model.addAttribute("user", user);
        return "tickets/one";
    }
}
