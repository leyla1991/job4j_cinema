package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.filmSession.FilmSessionService;
import ru.job4j.cinema.service.ticket.TicketService;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {

    private FilmSessionService filmSessionService;

    private TicketService ticketService;

    private TicketController ticketController;

    @BeforeEach
    public void initServices() {
        filmSessionService = mock(FilmSessionService.class);
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(ticketService, filmSessionService);
    }


    @Test
    public void whenRequestTicketBuyPageThenGetPageWithFilmSession() {
        var ticket1 = new Ticket(1, 1, 1, 3, 1);
        var expectedBuyTicket = ticket1;
        MockHttpServletRequest request = new MockHttpServletRequest();
        when(ticketService.save(ticket1)).thenReturn(Optional.of(expectedBuyTicket));

        var model = new ConcurrentModel();
        var view = ticketController.buyTicket(ticket1, model, request);
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
        var view = ticketController.buyTicket(ticket1, model, request);
        var actualException = model.getAttribute("message");

        when(ticketController.buyTicket(ticket2, model, request)).thenThrow(expectedException);

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualException).isEqualTo(expectedException.getMessage());
    }

}