package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cinema.dto.FilmSessionDTO;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.filmSession.FilmSessionService;
import ru.job4j.cinema.service.ticket.TicketService;
import ru.job4j.cinema.service.user.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/users")
@ThreadSafe
public class UserController {
    private final UserService userService;
    private final TicketService ticketService;
    private final FilmSessionService filmSessionService;

    public UserController(UserService userService, TicketService ticketService, FilmSessionService filmSessionService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.filmSessionService = filmSessionService;
    }

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "users/register";
    }

    @PostMapping("/register")
    public String register(Model model, @ModelAttribute User user) {
        var savedUser = userService.save(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "users/register";
        }
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute User user, Model model, HttpServletRequest request) {
        var userOptional = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Почта или пароль введены неверно");
            return "users/login";
        }
        var session = request.getSession();
        session.setAttribute("user", userOptional.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

    @GetMapping("/all")
    public String getById(Model model, HttpSession session) {
        var user = (User) session.getAttribute("user");
        Map<Integer, FilmSessionDTO> map = new HashMap<>();
        var u = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
        if (u.isEmpty()) {
            model.addAttribute("message", "Пользователь не найден");
            return "users/register";
        }
        var tickets = ticketService.findByUserId(user.getId());
        for (Ticket t : tickets) {
            var filmSession = filmSessionService.findById(t.getSessionId()).get();
            map.put(t.getSessionId(), filmSession);
        }
        model.addAttribute("ticket", tickets);
        model.addAttribute("filmSession", map);
        return "users/ticketsUser";
    }
}
