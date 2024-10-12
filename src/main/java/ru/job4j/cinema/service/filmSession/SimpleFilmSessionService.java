package ru.job4j.cinema.service.filmSession;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDTO;
import ru.job4j.cinema.dto.FilmSessionDTO;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Halls;
import ru.job4j.cinema.repository.filmSession.FilmSessionRepository;
import ru.job4j.cinema.service.film.FilmService;
import ru.job4j.cinema.service.halls.HallsService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;

    private final FilmService filmService;

    private final HallsService hallsService;

    public SimpleFilmSessionService(FilmSessionRepository sql2oFilmSessionRepository, FilmService sql2oFilmService, HallsService sql2oHallsService) {
        this.filmSessionRepository = sql2oFilmSessionRepository;
        this.filmService = sql2oFilmService;
        this.hallsService = sql2oHallsService;
    }

    @Override
    public Optional<FilmSessionDTO> findById(int id) {
        var filmSessionOptional = filmSessionRepository.findById(id);
        if (filmSessionOptional.isEmpty()) {
            return Optional.empty();
        }
        FilmSession filmSession = filmSessionOptional.get();
        FilmDTO film = getFilm(filmSession.getFilmId());
        Halls halls = getHalls(filmSession.getHallsId());
        FilmSessionDTO filmSessionDTO = new FilmSessionDTO(filmSession.getFilmId(), film, halls,
                filmSession.getStartId(), filmSession.getEndTime(), filmSession.getPrice());
        filmSessionDTO.setId(filmSession.getId());
        return Optional.of(filmSessionDTO);
    }

    @Override
    public Collection<FilmSessionDTO> findAll() {
        return filmSessionRepository.findAll().stream()
                .map(filmSession -> {
                    FilmDTO filmName = getFilm(filmSession.getFilmId());
                    Halls halls = getHalls(filmSession.getHallsId());
                    FilmSessionDTO filmSessionDTO = new FilmSessionDTO(filmSession.getFilmId(), filmName, halls,
                            filmSession.getStartId(), filmSession.getEndTime(), filmSession.getPrice());
                    filmSessionDTO.setId(filmSession.getId());
                    return filmSessionDTO;
                        }).collect(Collectors.toList());
    }

    public FilmDTO getFilm(Integer id) {
        return filmService.findById(id).get();
    }

    public Halls getHalls(Integer id) {
        return hallsService.findById(id).get();
    }
}
