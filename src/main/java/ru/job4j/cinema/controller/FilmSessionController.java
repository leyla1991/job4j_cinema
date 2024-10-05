package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.TicketService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/schedules")
@ThreadSafe
public class FilmSessionController {

    private final FilmSessionService service;

    private final TicketService ticketService;

    public FilmSessionController(FilmSessionService service, TicketService ticketService) {
        this.service = service;
        this.ticketService = ticketService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("schedules", service.findAll());
        return "schedules/list";
    }

    @GetMapping("/{id}")
    public String getById(Model model, @PathVariable int id, HttpSession session) {
        var user = (User) session.getAttribute("user");
        var filmSessionDTO = service.findById(id);
        if (filmSessionDTO.isEmpty()) {
            model.addAttribute("message", "Билетов с указанным идентификатором не найдена");
            return "errors/404";
        }
        if (user == null) {
            model.addAttribute("message", "Пройдите регистрацию");
            return "redirect:/users/login";
        }
        model.addAttribute("schedule", filmSessionDTO.get());
        model.addAttribute("sessionId", id);
        model.addAttribute("user", user);
        return "tickets/buy";
    }

    @PostMapping("/buy")
    public String buyTicket(@ModelAttribute Ticket ticket, Model model, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        var optionalTicket = ticketService.save(ticket);
         if (optionalTicket.isEmpty()) {
            model.addAttribute("message", "Данные места заняты");
            return "errors/404";
        }
         model.addAttribute("ticket", ticket);
         model.addAttribute("film", service.findById(ticket.getSessionId()));
         model.addAttribute("user", user);
        return "tickets/one";
    }
}
