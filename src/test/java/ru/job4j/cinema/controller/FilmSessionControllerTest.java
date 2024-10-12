package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDTO;
import ru.job4j.cinema.dto.FilmSessionDTO;
import ru.job4j.cinema.model.Halls;
import ru.job4j.cinema.service.schedules.FilmSessionService;
import ru.job4j.cinema.service.ticket.TicketService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class FilmSessionControllerTest {

    private FilmSessionService filmSessionService;

    private TicketService ticketService;

    private FilmSessionController filmSessionController;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        ticketService = mock(TicketService.class);
        filmSessionController = new FilmSessionController(filmSessionService);
    }

    @Test
    public void whenRequestFilmSessionListPageThenGetPageWithFilmSession() {
        var filmDTO =  new FilmDTO("name", "description", 2023, 12, 320, "genre", 1);
        var halls = new Halls(1, "name", 3, 3, "description");
        var filmSession1 = new FilmSessionDTO(1, filmDTO, halls, LocalDateTime.now(), LocalDateTime.now(), 100);
        var filmSession2 = new FilmSessionDTO(1, filmDTO, halls, LocalDateTime.now(), LocalDateTime.now(), 75);
        var expectedFilmSession = List.of(filmSession1, filmSession2);
        when(filmSessionService.findAll()).thenReturn(expectedFilmSession);

        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var actualFilmSession = model.getAttribute("schedules");

        assertThat(view).isEqualTo("schedules/list");
        assertThat(actualFilmSession).isEqualTo(expectedFilmSession);
    }
}