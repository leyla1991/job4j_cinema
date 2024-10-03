package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDTO;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    private final GenreService genreService;

    public SimpleFilmService(FilmRepository sql2oFilmRepository, GenreService sql2oGenreService) {
        this.filmRepository = sql2oFilmRepository;
        this.genreService = sql2oGenreService;
    }

    @Override
    public Optional<FilmDTO> findById(int id) {
        var filmOptional = filmRepository.findById(id);
        if (filmOptional.isEmpty()) {
            return Optional.empty();
        }
        Film film = filmOptional.get();
        String genreName = getGenreName(film.getId());
        ru.job4j.cinema.dto.FilmDTO filmDTO = new ru.job4j.cinema.dto.FilmDTO(film.getName(), film.getDescription(), film.getYear(),
                film.getYear(), film.getDurationInMinutes(), genreName, film.getFileId());
        filmDTO.setId(film.getId());
        return Optional.of(filmDTO);
    }

    @Override
    public Collection<FilmDTO> findAll() {
        return filmRepository.findAll().stream().map(film -> {
            String genreName = getGenreName(film.getGenreId());
            FilmDTO filmDTO =  new FilmDTO(film.getName(), film.getDescription(),
                    film.getYear(), film.getMinimalAge(), film.getDurationInMinutes(),
                    genreName, film.getFileId());
            filmDTO.setId(film.getId());
            return filmDTO;
                }).collect(Collectors.toList());
    }

    private String getGenreName(Integer id) {
        return genreService.findById(id).get().getName();
    }
}
