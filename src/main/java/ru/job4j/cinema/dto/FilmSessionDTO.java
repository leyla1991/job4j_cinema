package ru.job4j.cinema.dto;

import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Halls;

import java.time.LocalDateTime;

public class FilmSessionDTO {

    private int id;

    private int filmId;

    private FilmDTO film;

    private Halls halls;

    private LocalDateTime startId;

    private LocalDateTime endTime;

    private int price;

    public FilmSessionDTO() {
    }

    public FilmSessionDTO(int filmId, FilmDTO film, Halls halls,
                          LocalDateTime startId, LocalDateTime endTime, int price) {
        this.filmId = filmId;
        this.film = film;
        this.halls = halls;
        this.startId = startId;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public FilmDTO getFilm() {
        return film;
    }

    public void setFilm(FilmDTO film) {
        this.film = film;
    }

    public Halls getHalls() {
        return halls;
    }

    public void setHalls(Halls halls) {
        this.halls = halls;
    }

    public LocalDateTime getStartId() {
        return startId;
    }

    public void setStartId(LocalDateTime startId) {
        this.startId = startId;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
