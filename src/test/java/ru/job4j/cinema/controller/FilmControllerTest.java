package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDTO;
import ru.job4j.cinema.service.film.FilmService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class FilmControllerTest {

    private FilmService filmService;

    private FilmController filmController;

    @BeforeEach
    public void init() {
        filmService = mock(FilmService.class);
        filmController = new FilmController(filmService);
    }

    @Test
    public void whenFilmAll() {
        var filmDTO1 = new FilmDTO("name", "desc", 2024, 12, 120, "genre", 1);
        var filmDTO2 = new FilmDTO("name1", "desc1", 2024, 12, 120, "genre1", 2);
        var expectedFilm = List.of(filmDTO1, filmDTO2);

        when(filmService.findAll()).thenReturn(expectedFilm);

        var model = new ConcurrentModel();
        var view = filmController.getAll(model);
        var actualFilm = model.getAttribute("films");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualFilm).isEqualTo(expectedFilm);
    }

    @Test
    public void whenFindFilm() {
        var filmDTO = new FilmDTO("name", "desc", 2024, 12, 120, "genre", 1);
        var expectedFilm = filmDTO;

        when(filmService.findById(filmDTO.getId())).thenReturn(Optional.of(expectedFilm));

        var model = new ConcurrentModel();
        var view = filmController.getFindById(model, 0);
        var actualFilm = model.getAttribute("films");

        assertThat(view).isEqualTo("schedules/buy");
        assertThat(actualFilm).isEqualTo(Optional.of(expectedFilm));
    }

    @Test
    public void whenFilmNotByFind() {
        var expectedFilm = new RuntimeException("Фильм с таким названием не найден");

        when(filmService.findById(1)).thenThrow(expectedFilm);

        var model = new ConcurrentModel();
        var view = filmController.getFindById(model, 0);
        var actualFilm = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualFilm).isEqualTo(expectedFilm.getMessage());
    }
}