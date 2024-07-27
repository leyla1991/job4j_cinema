package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.MemoryFilmRepository;

@Controller
@RequestMapping("/films")
public class FilmController {

    private final FilmRepository filmRepository = MemoryFilmRepository.getInstance();

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("films", filmRepository.findAll());
        return "films/list";
    }

}
