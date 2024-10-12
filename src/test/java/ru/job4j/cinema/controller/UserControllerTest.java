package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.filmSession.FilmSessionService;
import ru.job4j.cinema.service.ticket.TicketService;
import ru.job4j.cinema.service.user.UserService;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserService userService;

    private TicketService ticketService;

    private FilmSessionService filmSessionService;

    private UserController userController;

    @BeforeEach
    public void init() {
        userService = mock(UserService.class);
        ticketService = mock(TicketService.class);
        filmSessionService = mock(FilmSessionService.class);
        userController = new UserController(userService, ticketService, filmSessionService);
    }

    @Test
    public void whenGetRegistration() {
        var user1 = new User();
        var argumentUserCaptor = ArgumentCaptor.forClass(User.class);

        when(userService.save(argumentUserCaptor.capture())).thenReturn(Optional.of(user1));
        var model = new ConcurrentModel();
        var view = userController.register(model, user1);
        var actualUser = argumentUserCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/users/login");
        assertThat(actualUser).isEqualTo(user1);
    }

    @Test
    public void whenLoginUser() {
        var user = new User("password", "email", "name", 1);
        MockHttpServletRequest request = new MockHttpServletRequest();
        var argumentUserPasswordCaptor = ArgumentCaptor.forClass(String.class);
        var argumentUserEmailCaptor = ArgumentCaptor.forClass(String.class);
        when(userService.findByEmailAndPassword(argumentUserEmailCaptor.capture(),
                argumentUserPasswordCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);
        var actualEmailAndPass = List.of(argumentUserEmailCaptor.getValue(), argumentUserPasswordCaptor.getValue());
        var expected = List.of(user.getEmail(), user.getPassword());

        assertThat(view).isEqualTo("redirect:/index");
        assertThat(actualEmailAndPass).isEqualTo(expected);
    }
}