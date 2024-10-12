package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.service.film.FilmService;

@ThreadSafe
@Controller
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmService.findAll());
        return "films/list";
    }

    @PostMapping("/{findById}")
    public String getFindById(Model model, @PathVariable int id) {
        var film = filmService.findById(id);
        if (film.isEmpty()) {
            model.addAttribute("message", "Фильм с таким названием не найден");
            return "errors/404";
        }
        model.addAttribute("films", filmService.findById(id).stream().findAny());
        return "schedules/buy";
    }
}
