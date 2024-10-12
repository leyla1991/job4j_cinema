package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.schedules.FilmSessionService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/schedules")
@ThreadSafe
public class FilmSessionController {

    private final FilmSessionService service;

    public FilmSessionController(FilmSessionService service) {
        this.service = service;
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
            model.addAttribute("message", "Билеты с указанным идентификатором не найдены");
            return "errors/404";
        }
        model.addAttribute("schedule", filmSessionDTO.get());
        model.addAttribute("sessionId", id);
        model.addAttribute("user", user);
        return "tickets/buy";
    }
}
