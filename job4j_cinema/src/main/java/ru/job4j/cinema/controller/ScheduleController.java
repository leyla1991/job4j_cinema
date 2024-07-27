package ru.job4j.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.repository.MemoryScheduleRepository;
import ru.job4j.cinema.repository.ScheduleRepository;

@Controller
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleRepository scheduleRepository = MemoryScheduleRepository.getInstance();

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("schedules", scheduleRepository.findAll());
        return "schedules/list";
    }
}
