package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDTO;
import ru.job4j.cinema.dto.FilmSessionDTO;
import ru.job4j.cinema.model.Halls;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.TicketService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        filmSessionController = new FilmSessionController(filmSessionService, ticketService);
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

    @Test
    public void whenRequestTicketBuyPageThenGetPageWithFilmSession() {
        var ticket1 = new Ticket(1, 1, 1, 3, 1);
        var expectedBuyTicket = ticket1;
        MockHttpServletRequest request = new MockHttpServletRequest();
        when(ticketService.save(ticket1)).thenReturn(Optional.of(expectedBuyTicket));

        var model = new ConcurrentModel();
        var view = filmSessionController.buyTicket(ticket1, model, request);
        var actualBuyTicket = model.getAttribute("ticket");

        assertThat(view).isEqualTo("tickets/one");
        assertThat(actualBuyTicket).isEqualTo(expectedBuyTicket);
    }

    @Test
    public void whenErrorBuy() {
        var ticket1 = new Ticket(1, 1, 1, 1, 1);
        var ticket2 = new Ticket(2, 1, 1, 1, 1);
        var expectedException = new RuntimeException("Данные места заняты");
        MockHttpServletRequest request = new MockHttpServletRequest();

        var model = new ConcurrentModel();
        var view = filmSessionController.buyTicket(ticket1, model, request);
        var actualException = model.getAttribute("message");

        when(filmSessionController.buyTicket(ticket2, model, request)).thenThrow(expectedException);

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualException).isEqualTo(expectedException.getMessage());
    }
}